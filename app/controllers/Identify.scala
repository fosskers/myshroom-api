package controllers

// PLAY
import play.api._
import play.api.mvc._

// OTHER
import scala.concurrent.Future

// --- //

class Identify extends Controller {

  def fromURL(url: String) = Action.async {
    Future.successful(Ok(s"You said: ${url}"))
  }
}
