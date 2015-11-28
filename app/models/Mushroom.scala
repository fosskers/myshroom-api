package models

// PLAY
import play.api.libs.json.Json

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
