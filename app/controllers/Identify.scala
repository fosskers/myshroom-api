package controllers

// PLAY
import play.api._
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._
import play.api.Play.current

// OTHER
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// --- //

class Identify extends Controller {

  private val confidentJ: String => JsValue = url => Json.obj(
    "source" -> url,
    "status" -> "CONFIDENT",
    "result" -> Json.obj(
      "latin"        -> "Amanita muscaria",
      "variety"      -> "muscaria",  // There are others.
      "common"       -> Json.arr("Fly Agaric", "Fly Amanita"),
      "poisonous"    -> true,
      "psychoactive" -> true,
      "habitat"      -> "Native to conifer and deciduous woodlands throughout the temperate and boreal regions of the Northern Hemisphere."
    )
  )

  /* 2015 November 23 @ 21:45
   * In future, this will accept label and confidence value pairs
   * from the Neural Network server, and decide how to interpret
   * the data. For now, we return some place-holder JSON.
   */
  def fromURL(url: String) = Action.async {

    val resp = WS.url("http://localhost:8000/identify")
      .withHeaders("Accept" -> "application/json")
      .withRequestTimeout(5000)  // 5 seconds
      .withQueryString("identify" -> url)

    resp.get.map(r => Ok(r.json))
  }
}
