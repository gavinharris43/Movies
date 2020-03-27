package models

import akka.http.scaladsl.model.DateTime

case class Movies(movieTitle: String, images: List[String],dateRelease: String, actors: List[String], directors: List[String])

object  Movies{
  def apply(movieTitle: String,
            images: List[String],
            dateRelease: FilmDate,
            actors: List[String],
            directors: List[String]):
  Movies = new Movies(movieTitle, images , dateRelease.toString, actors, directors)



  def getNewest5(movies: List[Movies]){

  }
}