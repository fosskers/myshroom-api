package controllers

// PLAY
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._

// LOCAL
import models.Mushroom
import models.Mushrooms._

// OTHER
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// --- //

class Api @Inject() (ws: WSClient, db: ShroomDB) extends Controller {

  /*
  private val confidentJ: String => JsValue = url => Json.obj(
    "source" -> url,
    "status" -> "CONFIDENT",
    "result" -> Json.obj( ... json from DB ...)
  )
   */

  /* 2015 November 25 @ 15:11
   * At the moment, this expects some server returning nice JSON to be
   * running on Port 8000. The `shroom-vision-dummy` server does this,
   * but produces random results, so that this interaction can be tested.
   */
  def byURL(url: String) = Action.async {

    val call = ws.url("http://localhost:8000/identify")
      .withHeaders("Accept" -> "application/json")
      .withRequestTimeout(5000) // 5 seconds
      .withQueryString("url" -> url)

    call.get.map(r => Ok(r.json)).recover({
      case e: Throwable => InternalServerError(
        Json.obj("error" -> "Couldn't access mushroom identification server.")
      )
    })
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
      case ((_,None), acc) => acc
      case ((s,Some(b)), acc) => (s -> JsBoolean(b)) +: acc
    })

    db.jsonQuery(JsObject(params)).map(shrooms => Ok(Json.toJson(shrooms)))
  }
}
