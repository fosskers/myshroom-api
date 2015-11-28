package models

// PLAY
import play.api.libs.json.Json
import play.api.data._
import play.api.data.Forms._
import play.modules.reactivemongo.json._

// --- //

case class Mushroom(
  latin: String,
  common: String,
  poisonous: Boolean,
  psychoactive: Boolean,
  habitat: String
)

object Mushrooms {
  /* Automatic conversion from JSON */
  implicit val mushroomFormat = Json.format[Mushroom]
}
