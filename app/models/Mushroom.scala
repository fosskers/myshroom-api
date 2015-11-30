package models

// PLAY
import play.api.libs.json.Json

// --- //

case class Mushroom(
  latin: String, // Latin names are unique for mushrooms.
  common: Seq[String],
  poisonous: Boolean,
  psychoactive: Boolean,
  regions: Seq[String],
  confusedWith: Seq[String],
  habitat: String
)

object Mushrooms {
  /* Automatic conversion from JSON */
  implicit val mushroomFormat = Json.format[Mushroom]
}
