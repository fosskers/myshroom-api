package controllers

import extra.Forms._
import javax.inject.Inject
import models.Contact
import models.Contacts._
import play.api.i18n._
import play.api.mvc._
import play.modules.reactivemongo._
import play.modules.reactivemongo.json.collection._
import reactivemongo.api.commands.WriteResult
import scala.concurrent.{ExecutionContext, Future}

// --- //

class Contacts @Inject() (
  val reactiveMongoApi: ReactiveMongoApi,
  val messagesApi: MessagesApi
) extends Controller
  with MongoController with ReactiveMongoComponents with I18nSupport {

  def collection: JSONCollection = db.collection[JSONCollection]("contacts")

  def in(implicit ec: ExecutionContext) = Action.async { implicit request =>
    contactForm.bindFromRequest.fold(
      formWithErrors => Future.successful(Ok(views.html.index(urlForm))),
      contact => collection
        .insert(contact)
        .map(_ => Ok(views.html.index(urlForm)))
        .recover({
          case e: Throwable => Ok(views.html.index(urlForm)) // TODO!
        })
    )
  }
}
