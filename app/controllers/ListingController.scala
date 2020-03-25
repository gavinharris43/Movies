package controllers

import akka.http.scaladsl.model.DateTime
import javax.inject._
import models.{FilmDate, Movies}
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ListingController @Inject()(cc: ControllerComponents, val movieController: MovieController) extends AbstractController(cc) {


  val test = Movies(
    "Persona 5",
    List[String]("images/film-photos/persona5/img1.jpg", "images/film-photos/persona5/img2.jpg", "images/film-photos/persona5/img3.jpg", "images/film-photos/persona5/img4.jpg"),
    FilmDate(Tuple3(25,3,2020),Tuple2(11,15)),
    List[String]("Juamal", "Jack"),
    List[String]("Gavin", "Dan", "Kobby")
  )


  def listingIndex = Action.async {
    movieController.createMovie(test)
    movieController.findByName("Persona 5").map(film => Ok(views.html.listing(film.head)))
  }

}
