package controllers
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import reactivemongo.play.json.collection.JSONCollection
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.{FilmDate, Movies}
import models.JsonFormats._
import play.api.libs.json.{JsValue, Json}
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult
import scala.concurrent.ExecutionContext.Implicits.global



class MovieController @Inject()(
                                 val reactiveMongoApi: ReactiveMongoApi
                               ) extends ReactiveMongoComponents {

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("movies"))


  def createMovie(movie: Movies): Future[WriteResult] = {
    collection.flatMap(_.insert.one(movie))
  }

  def findByName(title : String): Future[List[Movies]] = {
    collection.map {
      _.find(Json.obj("movieTitle" -> title))
        .sort(Json.obj())
        .cursor[Movies]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Movies]]()
      )
    )
  }
  def findAll(): Future[List[Movies]] = {
    collection.map {
      _.find(Json.obj())
        .sort(Json.obj())
        .cursor[Movies]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Movies]]()
      )
    )
  }

  def getFilmDateYear(filmDate: String): Int ={
    Integer.valueOf(filmDate.substring(6, 10))
  }
  def getFilmDateMonth(filmDate: String): Int ={
    Integer.valueOf(filmDate.substring(3, 5))
  }
  def getFilmDateDay(filmDate: String): Int ={
    Integer.valueOf(filmDate.substring(0, 2))
  }
}
