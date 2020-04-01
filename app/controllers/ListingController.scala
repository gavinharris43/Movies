package controllers

import akka.http.scaladsl.model.DateTime
import javax.inject._
import models.{FilmDate, Movies}
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global
import helpers.{FilmDateControl}
import org.joda.time.{ DateTime, DateTimeZone }
import org.joda.time.format.DateTimeFormat

@Singleton
class ListingController @Inject()(cc: ControllerComponents, val movieController: MovieController, val filmDateControl: FilmDateControl) extends AbstractController(cc) {


  val personaTest = Movies(
    "Persona 5",
    List[String]("images/film-photos/persona5/img1.jpg", "images/film-photos/persona5/img2.jpg", "images/film-photos/persona5/img3.jpg", "images/film-photos/persona5/img4.jpg"),
    FilmDate(Tuple3(25,3,2010),Tuple2(11,15)),
    List[String]("Juamal", "Jack"),
    List[String]("Gavin", "Dan", "Kobby")
  )

  val sonicTest = Movies(
    "Sonic",
    List[String]("images/film-photos/Sonic/img1.jpg", "images/film-photos/Sonic/img2.jpg", "images/film-photos/Sonic/img3.jpg", "images/film-photos/Sonic/img4.jpg"),
    FilmDate(Tuple3(25,2,1999),Tuple2(11,15)),
    List[String]("Bob", "Phil"),
    List[String]("Gavin", "Dan", "Kobby")
  )
  val powerRangersTest = Movies(
    "PowerRangers",
    List[String]("images/film-photos/PowerRangers/img1.jpg", "images/film-photos/PowerRangers/img2.jpg", "images/film-photos/PowerRangers/img3.jpg", "images/film-photos/PowerRangers/img4.jpg"),
    FilmDate(Tuple3(25,5,1999),Tuple2(11,15)),
    List[String]("Bob", "Phil"),
    List[String]("Gavin", "Dan", "Kobby")
  )


  def listingGalleryIndex = Action.async {
//    movieController.createMovie(personaTest)
//    movieController.createMovie(sonicTest)
//    movieController.createMovie(powerRangersTest)
    movieController.findAll().map(movies => Ok(views.html.listingGallery(movies.sortWith(filmDateControl.sortByDateTime(_,_)))))
  }
  def newListingGalleryIndex = Action.async {
    movieController.findAll().map(movies => Ok(views.html.newListingGallery(movies.sortWith(filmDateControl.sortByDateTime(_,_)).take(6))))
  }
  def listingIndex(title:String) = Action.async {
    movieController.findByName(title).map(film => Ok(views.html.listing(film.head)))
  }


}
