package controllers

import extra.Forms._
import javax.inject.Inject
import models._
import models.Mushrooms._
import play.api.Play.current
import play.api.i18n._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._
import scala.concurrent.{Future, blocking}
import scala.sys.process._
import play.api.Logger

// --- //

class Api @Inject() (
  ws: WSClient,
  db: ShroomDB,
  val messagesApi: MessagesApi
) extends Controller with I18nSupport {

  def fromForm = Action.async { implicit request =>
    urlForm.bindFromRequest.fold(
      formWithErrors => Future.successful(
        Ok(views.html.index())
      ),
      url => callNet(url).map(r => Ok(views.html.id_result(url, r)))
    )
  }

  /* 2015 November 25 @ 15:11
   * At the moment, this expects some server returning nice JSON to be
   * running on Port 8000. The `shroom-vision-dummy` server does this,
   * but produces random results, so that this interaction can be tested.
   */
  def byURL(url: String) = Action.async {
    callNet(url).map({
      case None => InternalServerError(
        Json.obj("error" -> "Error processing results from Neural Net.")
      )
      case Some(r) => Ok(Json.toJson(r))
    })
  }

  /* 2015 December  6 @ 20:32
   * `godScript` downloads the given image via `wget`, then runs a myriad
   * of other shell scripts to interact with the Caffe model and return
   * nicely formatted JSON.
   */
  private def callNet(url: String): Future[Option[IdResult]] = {
    try {
      val json = Json.parse(blocking(s"godScript ${url}".!!))

      val names: Seq[String] = (json \\ "label").map(_.as[String])
      val confs: Seq[Float] = (json \\ "confidence").map(_.as[Float])

      db.byLatins(names).map({ shrooms =>
        Some(sanitize(url, shrooms.zip(confs)))
      })
    } catch {
      case e: Throwable => Future.successful(None)
    }
  }

  /* Produce sanitized results that the user can appreciate */
  private def sanitize(
    url: String,
    shrooms: Seq[(Mushroom, Float)]
  ): IdResult = shrooms match {
    case Seq() => IdResult(url, IdStatus("ERROR", 1), None, Seq())
    case _ => {
      /* Sorted by descending confidence value */
      val sorted = shrooms.sortWith(_._2 > _._2)

      val status = sorted.head._2 match {
        case con if con > 0.95 => ("CONFIDENT", con)
        case con if con > 0.80 => ("LIKELY", con)
        case con if con > 0.65 => ("MAYBE", con)
        case con => ("UNSURE", con)
      }

      /* Check the remaining matches for warnings */
      val warnings = sorted.tail.foldRight(Seq[String]()) { (s, acc) =>

        val warning: Option[String] = s._1.attributes match {
          case a if a.deadly => Some("deadly")
          case a if a.poisonous => Some("poisonous")
          case a if a.psychoactive => Some("psychoactive")
          case _ => None
        }

        warning match {
          case None => acc
          case Some(w) => "There is a %.2f%% chance this mushroom is a %s %s!"
            .format(s._2 * 100, w, s._1.latin) +: acc
        }
      }

      IdResult(
        url, IdStatus(status._1, status._2), Some(sorted.head._1), warnings
      )
    }
  }

  def byLatin(latin: String) = Action.async {

    db.byLatin(latin).map({
      case None => Ok(Json.obj("error" -> s"Invalid Latin name: ${latin}"))
      case Some(s) => Ok(Json.toJson(s))
    })
  }

  def find(
    psycho: Option[Boolean],
    poison: Option[Boolean],
    deadly: Option[Boolean],
    rings: Option[Boolean],
    cap: Option[String],
    hymenium: Option[String],
    spores: Option[String],
    ecology: Option[String],
    region: Option[String]
  ) = Action.async {

    val pairs = Seq(
      ("attributes.psychoactive", psycho.map(JsBoolean)),
      ("attributes.poisonous", poison.map(JsBoolean)),
      ("attributes.deadly", deadly.map(JsBoolean)),
      ("fairyRings", rings.map(JsBoolean)),
      ("attributes.cap", cap.map(c => Json.obj("$in" -> Seq(c)))),
      ("attributes.hymenium", hymenium.map(h => Json.obj("$in" -> Seq(h)))),
      ("attributes.sporePrint", spores.map(s => Json.obj("$in" -> Seq(s)))),
      ("attributes.ecology", ecology.map(e => Json.obj("$in" -> Seq(e)))),
      ("regions", region.map(r => Json.obj("$in" -> Seq(r))))
    )

    /* Scala's typechecker needs handholding */
    val zero: Seq[(String, JsValue)] = Seq()
    val params = pairs.foldRight(zero)({
      case ((_, None), acc) => acc
      case ((s, Some(b)), acc) => (s -> b) +: acc
    })

    db.jsonQuery(JsObject(params)).map(shrooms => Ok(Json.toJson(shrooms)))
  }
}
