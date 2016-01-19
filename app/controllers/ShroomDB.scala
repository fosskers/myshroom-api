package controllers

import javax.inject.Inject
import models.Mushroom
import models.Mushrooms._
import play.api.libs.json._
import play.api.mvc.Controller
import play.modules.reactivemongo._
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection._
import reactivemongo.api.ReadPreference._
import scala.concurrent.{ExecutionContext, Future}

// --- //

class ShroomDB @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  def collection: JSONCollection = db.collection[JSONCollection]("shrooms")

  def jsonQuery(
    json: JsObject,
    limit: Option[Int]
  )(implicit ec: ExecutionContext): Future[Seq[Mushroom]] = {
    collection.find(json)
      .cursor[Mushroom](readPreference = nearest)
      .collect[Seq](upTo = limit.getOrElse(Int.MaxValue))
  }

  /* There should only ever be one result, as Latin names are unique */
  def byLatin(
    latin: String
  )(implicit ec: ExecutionContext): Future[Option[Mushroom]] = {
    collection.find(Json.obj("latin" -> latin)).one[Mushroom]
  }

  def byLatins(
    latins: Seq[String]
  )(implicit ec: ExecutionContext): Future[Seq[Mushroom]] = {
    jsonQuery(Json.obj("latin" -> Json.obj("$in" -> latins)), None)
  }
}
