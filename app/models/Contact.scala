package models

import play.api.libs.json.Json

// --- //

case class Contact(email: String)

object Contacts {
  /* Automatic conversion to and from JSON */
  implicit val contactFormat = Json.format[Contact]
}
