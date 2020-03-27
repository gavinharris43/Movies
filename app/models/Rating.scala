package models

case class Rating(title: String,
                  iconLocation: String,
                  description: String)

object Rating {
  def apply(title: String, iconLocation: String, description: String):
  Rating = new Rating(title, iconLocation, description)
}