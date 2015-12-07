package extra

// PLAY
import play.api.data.Forms._
import play.api.data._

// LOCAL
import extra.Constraints._

// --- //

object Forms {
  val urlForm = Form(single("url" -> text.verifying(validUrl)))
}
