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
  hymenium: Seq[String],
  sporePrint: Seq[String],
  ecology: Seq[String]
)

case class IdResults(
  source: String,
  status: IdStatus,
  result: Option[Mushroom],
  warnings: Seq[String]
)

case class IdStatus(
  label: String,
  confidence: Float
)

object Mushrooms {
  /* Automatic conversion to and from JSON */
  implicit val attributesFormat = Json.format[Attributes]
  implicit val mushroomFormat = Json.format[Mushroom]
  implicit val idStatusFormat = Json.format[IdStatus]
  implicit val resultsFormat = Json.format[IdResults]
}
