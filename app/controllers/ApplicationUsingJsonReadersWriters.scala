package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.{Feed, LoginDetails, User}
import models.JsonFormats._
import play.api.libs.json.{JsValue, Json}
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}

class ApplicationUsingJsonReadersWriters @Inject()(
                                                    components: ControllerComponents,
                                                    val reactiveMongoApi: ReactiveMongoApi
                                                  ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents with play.api.i18n.I18nSupport{

  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("persons"))
  def userCollection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("user"))

  def create: Action[AnyContent] = Action.async {
    val user = User(29, "John", "Smith", List(Feed("Slashdot news", "http://slashdot.org/slashdot.rdf")))
    val futureResult = collection.flatMap(_.insert.one(user))
    futureResult.map(_ => Ok("User inserted"))
  }

  def userFormDisplay(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.user(User.userForm))
  }

  def userFormSubmit(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
      User.userForm.bindFromRequest.fold({ formWithErrors =>
        Future(BadRequest(views.html.user(formWithErrors)))
      }, { userDetails =>
        val futureRes = userCollection.flatMap(_.insert.one(userDetails)).map {
          _ => Ok("User inserted")
        }
        futureRes
      })
  }

  def createFromJson: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[User].map { user =>
      collection.flatMap(_.insert.one(user)).map { _ => Ok("User inserted")
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def findByName(lastName: String): Action[AnyContent] = Action.async {
    val cursor: Future[Cursor[User]] = collection.map {
      _.find(Json.obj("lastName" -> lastName)).
        sort(Json.obj("created" -> -1)).
        cursor[User]()
    }

    val futureUsersList: Future[List[User]] =
      cursor.flatMap(
        _.collect[List](
          -1,
          Cursor.FailOnError[List[User]]()
        )
      )

    futureUsersList.map { persons =>
      Ok(persons.toString)
    }
  }



}
