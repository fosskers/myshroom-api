package controllers

// PLAY
import play.api._
import play.api.mvc._
//import play.api.routing._
//import routes.javascript._

// --- //

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  /*
  def javascriptRoutes = Action { implicit request =>
    Ok(JavaScriptReverseRouter("jsRoutes")(
      Identify.fromURL
    )).as("text/javascript")
  }
   */
}
