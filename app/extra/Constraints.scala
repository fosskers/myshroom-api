package extra

import play.api.data.validation._

// --- //

object Constraints {
  val validUrl: Constraint[String] = Constraint("constraints.url") { url =>
    try {
      new java.net.URL(url) // Can throw `MalformedURLException`.
      Valid
    } catch {
      case e: Throwable => Invalid(Seq(ValidationError("URL given was not valid.")))
    }
  }
}
