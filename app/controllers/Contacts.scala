package controllers

// PLAY
import play.api.mvc._
import play.modules.reactivemongo._
import play.modules.reactivemongo.json.collection._
import play.api.i18n._

// LOCAL
import extra.Forms._
import models.Contact
import models.Contacts._

// OTHER
import javax.inject.Inject
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
      formWithErrors => Future.successful(
        Ok(views.html.index(urlForm))
      ),
      contact => collection
        .insert(contact)
        .map(_ => Ok(views.html.index(urlForm)))
        .recover({
          case e: Throwable => Ok(views.html.index(urlForm))  // TODO!
        })
    )
  }
}
