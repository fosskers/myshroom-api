package models

// PLAY
import play.api.libs.json.Json

// --- //

case class Mushroom(
  latin: String, // Latin names are unique for mushrooms.
  common: Seq[String],
  confusedWith: Seq[String],
  regions: Seq[String],
  habitat: String,
  fairyRings: Boolean,
  attributes: Attributes
)

case class Attributes(
  psychoactive: Boolean,
  poisonous: Boolean,
  deadly: Boolean,
  cap: Seq[String],
  hymenium: String,
  sporePrint: Seq[String],
  ecology: String
)

object Mushrooms {
  /* Automatic conversion from JSON */
  implicit val attributesFormat = Json.format[Attributes]
  implicit val mushroomFormat = Json.format[Mushroom]
}
