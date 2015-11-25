package controllers

// PLAY
import play.api._
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._
import play.api.Play.current

// OTHER
import javax.inject.Inject;
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// --- //

class Identify @Inject() (ws: WSClient) extends Controller {

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

  /* 2015 November 25 @ 15:11
   * At the moment, this expects some server returning nice JSON to be
   * running on Port 8000. The `shroom-vision-dummy` server does this,
   * but produces random results, so that this interaction can be tested.
   */
  def fromURL(url: String) = Action.async {

    val call = ws.url("http://localhost:8000/identify")
      .withHeaders("Accept" -> "application/json")
      .withRequestTimeout(5000)  // 5 seconds
      .withQueryString("url" -> url)

    call.get.map(r => Ok(r.json))
  }
}
