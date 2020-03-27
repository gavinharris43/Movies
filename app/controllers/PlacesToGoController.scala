package controllers

import javax.inject.Inject
import models.JsonFormats._
import models.Place
import Services.MongoService
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection.{JSONCollection, _}

import scala.concurrent.{ExecutionContext, Future}

class PlacesToGoController @Inject()(components: ControllerComponents,
                                      val mongoService: MongoService
                                     ) extends AbstractController(components)
   with play.api.i18n.I18nSupport {

  implicit val ec: ExecutionContext = components.executionContext
  val collection: Future[JSONCollection] = mongoService.collection

  def placesToGo: Action[AnyContent] = Action{
    Ok(views.html.placestogo())
  }

  def places: Action[AnyContent] = Action{ implicit request: Request[AnyContent] =>
    Ok(views.html.places(Place.placesForm))
  }

  def submit: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Place.placesForm.bindFromRequest.fold({
      formWithErrors =>
        Future.successful(BadRequest(views.html.places(formWithErrors)))
    }, {
      place =>
        val cursor: Future[Cursor[Place]] = collection.map {
          _.find(Json.obj("placeName" -> place.placeName, "contact" -> place.contact)).
            sort(Json.obj("created" -> -1)).
            cursor[Place]()
        }

        val futureUsersList: Future[List[Place]] =
          cursor.flatMap(
            _.collect[List](
              -1,
              Cursor.FailOnError[List[Place]]()
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

  def getAllPlaces: Action[AnyContent] = Action.async {
    mongoService.findAllPlaces().map {
      placeList => Ok("Places: " + placeList.toString())
    }
  }


}
