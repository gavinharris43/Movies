package controllers

import javax.inject.Inject
import play.api.db
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}


class ClassificationsController @Inject()(cc: ControllerComponents,
                                          val reactiveMongoApi: ReactiveMongoApi) extends AbstractController(cc)
   with MongoController with ReactiveMongoComponents {

  implicit def ec: ExecutionContext = cc.executionContext
//  def collection: Future[JSONCollection] = db.collection[JSONCollection]("ratings")

  def classifications: Action[AnyContent] = Action {
    Ok(views.html.classifications())
  }

//  case class Rating(title: String,
//                   iconLocation: String,
//                   description: String)
//  val ratingU = Rating("U", "@routes.Assets.versioned('images/classifications/u.png')",
//    "A U film should be suitable for audiences aged four years and over, although it is impossible" +
//      "to predict what might upset any particular child.")
//  val ratingPG = Rating("PG", "@routes.Assets.versioned('images/classifications/pg.png')",
//    "General viewing, but some scenes may be unsuitable for young children. A PG film " +
//      "should not unsettle a child aged around eight or older. Unaccompanied children " +
//      "of any age may watch, but parents are advised to consider whether the content " +
//      "may upset younger, or more sensitive, children.")
//  val rating12 = Rating("12", "@routes.Assets.versioned('images/classifications/12.png')",
//    "Films classified 12A and video works classified 12 contain material that is not " +
//      "generally suitable for children aged under 12. No one younger than 12 may see a " +
//      "12A film in a cinema unless accompanied by an adult. Adults planning to take a " +
//      "child under 12 to view a 12A film should consider whether the film is suitable " +
//      "for that child. No one younger than 12 may rent or buy a 12 rated video work.")
//  val rating15 = Rating("15", "@routes.Assets.versioned('images/classifications/15.png')",
//    "No one younger than 15 may see a 15 film in a cinema. No one younger than 15 may " +
//      "rent or buy a 15 rated video work.")
//  val rating18 = Rating("18", "@routes.Assets.versioned('images/classifications/18.png')",
//    "No one younger than 18 may see an 18 film in a cinema. No one younger than 18 may " +
//      "rent or buy an 18 rated video work. Adults should be free to choose their own " +
//      "entertainment.")



//  def insertRatings = Action.async {
//
//    val futureResult = collection.insert(ratingU)
//    futureResult.map(_ => Ok)
//  }



}
