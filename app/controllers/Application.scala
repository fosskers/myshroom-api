package controllers

// PLAY
import play.api.mvc._
import play.api.i18n._

// LOCAL
import extra.Forms._

// OTHER
import javax.inject.Inject

// --- //

class Application @Inject() (
  val messagesApi: MessagesApi
) extends Controller with I18nSupport {

  def index = Action {
    Ok(views.html.index(urlForm))
  }
}
