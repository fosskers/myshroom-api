package controllers

// PLAY
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._

// LOCAL
import extra.Forms._
import models.Mushroom
import models.Mushrooms._

// OTHER
import javax.inject.Inject
import scala.concurrent.Future

// --- //

class Api @Inject() (ws: WSClient, db: ShroomDB) extends Controller {


  def fromForm = Action.async { implicit request =>
    urlForm.bindFromRequest.fold(
      formWithErrors => Future.successful(
        BadRequest(Json.obj("error" -> "Bad image url!"))
      ),
      url => callNet(url)
    )
  }

  /* 2015 November 25 @ 15:11
   * At the moment, this expects some server returning nice JSON to be
   * running on Port 8000. The `shroom-vision-dummy` server does this,
   * but produces random results, so that this interaction can be tested.
   */
  def byURL(url: String) = Action.async {
    callNet(url)
  }

  private def callNet(url: String): Future[Result] = {
    val call = ws.url("http://localhost:8000/identify")
      .withHeaders("Accept" -> "application/json")
      .withRequestTimeout(5000) // 5 seconds
      .withQueryString("url" -> url)

    call.get.flatMap({ r =>
      val names: Seq[String] = (r.json \\ "label").map(_.as[String])
      val confs: Seq[Float] = (r.json \\ "confidence").map(_.as[Float])

      db.byLatins(names).map({ shrooms =>
        Ok(sanitize(url, shrooms.zip(confs)))
      })
    }).recover({
      case e: Throwable => InternalServerError(
        Json.obj("error" -> "Couldn't access mushroom identification server.")
      )
    })
  }

  /* Produce sanitized results that the user can appreciate */
  private def sanitize(
    url: String,
    shrooms: Seq[(Mushroom, Float)]
  ): JsValue = shrooms match {
    case Seq() =>
      Json.obj(
        "source" -> url,
        "status" -> Json.obj("label" -> "ERROR", "confidence" -> 1),
        "result" -> Json.obj(),
        "warnings" -> Json.arr()
      )
    case _ => {
      /* Sorted by descending confidence value */
      val sorted = shrooms.sortWith(_._2 > _._2)

      val status = sorted.head._2 match {
        case con if con > 0.9 => ("CONFIDENT", con)
        case con if con > 0.75 => ("LIKELY", con)
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

      Json.obj(
        "source" -> url,
        "status" -> Json.obj("label" -> status._1, "confidence" -> status._2),
        "result" -> Json.toJson(sorted.head._1),
        "warnings" -> warnings
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
    deadly: Option[Boolean]
  ) = Action.async {

    val pairs = Seq(
      ("attributes.psychoactive", psycho),
      ("attributes.poisonous", poison),
      ("attributes.deadly", deadly)
    )

    val zero: Seq[(String, JsValue)] = Seq()
    val params = pairs.foldRight(zero)({
      case ((_, None), acc) => acc
      case ((s, Some(b)), acc) => (s -> JsBoolean(b)) +: acc
    })

    db.jsonQuery(JsObject(params)).map(shrooms => Ok(Json.toJson(shrooms)))
  }
}
