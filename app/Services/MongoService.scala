package Services

import javax.inject.Inject
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import reactivemongo.play.json._
import collection._
import models.Place
import play.api.libs.json.Json
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult
import models.JsonFormats._

class MongoService @Inject()(
                              val reactiveMongoApi: ReactiveMongoApi
                            ) extends ReactiveMongoComponents {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("Places"))
  def mailCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("Places"))
  def createPlace(user: Place): Future[WriteResult] = {
    collection.flatMap(_.insert.one(user))
  }

  def findAllPlaces(): Future[List[Place]] = {
    collection.map {
      _.find(Json.obj())
        .sort(Json.obj("created" -> -1))
        .cursor[Place]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Place]]()
      )
    )
  }

}
