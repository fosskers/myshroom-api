package controllers

// PLAY
import play.api._
import play.api.mvc._
import play.api.libs.json._

// OTHER
import scala.concurrent.Future

// --- //

class Identify extends Controller {
  private val djangoJson: String => JsValue = url => Json.obj(
    "source"  -> url,
    "results" -> Json.arr(
      Json.obj("label" -> "Fly Agaric", "confidence" -> 0.8),
      Json.obj("label" -> "Destroying Angel", "confidence" -> 0.15),
      Json.obj("label" -> "Oyster", "confidence" -> 0.05)
    )
  )

  private val confidentJ: String => JsValue = url => Json.obj(
    "source" -> url,
    "status" -> "CONFIDENT",
    "result" -> Json.obj(
      "latin"   -> "Amanita muscaria",
      "variety" -> "muscaria",  // There are others.
      "common"  -> Json.arr("Fly Agaric", "Fly Amanita"),
      "poisonous" -> true,
      "psychoactive" -> true,
      "habitat" -> "Native to conifer and deciduous woodlands throughout the temperate and boreal regions of the Northern Hemisphere."
    )
  )

  /* 2015 November 23 @ 21:45
   * In future, this will accept label and confidence value pairs
   * from the Neural Network server, and decide how to interpret
   * the data. For now, we return some place-holder JSON.
   */
  def fromURL(url: String) = Action.async {
    Future.successful(Ok(confidentJ(url)))
  }
}
