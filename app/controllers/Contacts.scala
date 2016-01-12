package controllers

import extra.Forms._
import javax.inject.Inject
import models.Contact
import models.Contacts._
import play.api.i18n._
import play.api.libs.concurrent.Execution.Implicits._
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

  def signUp = Action.async { implicit request =>
    contactForm.bindFromRequest.fold(
      formWithErrors => Future.successful(
        Redirect(routes.Application.index).flashing(
          "error" -> "Something went wrong, please try again."
        )
      ),
      contact => collection
        .insert(contact)
        .map(_ => Redirect(routes.Application.index).flashing(
          "success" -> s"Thank you, ${contact.email}! You'll hear from us."
        ))
        .recover({
          case e: Throwable => Redirect(routes.Application.index)
        })
    )
  }
}
