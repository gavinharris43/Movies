package controllers

import javax.inject.Inject
import models.JsonFormats._
import models.Places
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection.{JSONCollection, _}

import scala.concurrent.{ExecutionContext, Future}

class PlacesToGoController @Inject()(components: ControllerComponents,
                                      val reactiveMongoApi: ReactiveMongoApi
                                     ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("Places"))

  def placesToGo: Action[AnyContent] = Action{
    Ok(views.html.placestogo())
  }

  def places: Action[AnyContent] = Action{ implicit request: Request[AnyContent] =>
    Ok(views.html.places(Places.placesForm))
  }

  def submit: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Places.placesForm.bindFromRequest.fold({
      formWithErrors =>
        Future.successful(BadRequest(views.html.places(formWithErrors)))
    }, {
      place =>
        val cursor: Future[Cursor[Places]] = collection.map {
          _.find(Json.obj("placeName" -> place.placeName, "contact" -> place.contact)).
            sort(Json.obj("created" -> -1)).
            cursor[Places]()
        }

        val futureUsersList: Future[List[Places]] =
          cursor.flatMap(
            _.collect[List](
              -1,
              Cursor.FailOnError[List[Places]]()
            )
          )

        futureUsersList.map {
          value =>
            if (value.headOption.isEmpty) {
              collection.flatMap(_.insert.one(place)).map {
                _ => Ok("placeAdded")
              }
              Ok("placeAdded")
            }
            else BadRequest("Place Not Added")
        }
    })
  }


}
