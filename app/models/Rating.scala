package models

case class Rating(title: String,
                  iconLocation: String,
                  description: String)

object Rating {
  def apply(title: String, iconLocation: String, description: String):
  Rating = new Rating(title, iconLocation, description)

  val ratingU : Rating = Rating("U",  "@routes.Assets.versioned('images/classifications/u.png')",
    "ratingsText/U")
  val ratingPG: Rating = Rating("PG", "@routes.Assets.versioned('images/classifications/pg.png')",
    "ratingsText/PG")
  val rating12: Rating = Rating("12", "@routes.Assets.versioned('images/classifications/12.png')",
    "ratingsText/12")
  val rating15: Rating = Rating("15", "@routes.Assets.versioned('images/classifications/15.png')",
    "ratingsText/15")
  val rating18: Rating = Rating("18", "@routes.Assets.versioned('images/classifications/18.png')",
    "ratingsText/18")

  val ratings = Seq(ratingPG, rating12, rating15, rating18, ratingU)
}