package controllers

import javax.inject.Inject
import play.api.i18n._
import play.api.mvc._

// --- //

class Application @Inject() (
  val messagesApi: MessagesApi
) extends Controller with I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }
}
