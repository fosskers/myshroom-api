package extra

// PLAY
import play.api.data.Forms._
import play.api.data._

// LOCAL
import extra.Constraints._
import models.Contact

// --- //

object Forms {
  val urlForm = Form(single("url" -> text.verifying(validUrl)))

  val contactForm = Form(
    mapping(
      "email" -> email
    )(Contact.apply)(Contact.unapply)
  )
}
