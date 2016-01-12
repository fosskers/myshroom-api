package extra

import extra.Constraints._
import models.Contact
import play.api.data._
import play.api.data.Forms._

// --- //

object Forms {
  val urlForm = Form(single("url" -> text.verifying(validUrl)))

  val contactForm = Form(
    mapping(
      "email" -> email
    )(Contact.apply)(Contact.unapply)
  )
}
