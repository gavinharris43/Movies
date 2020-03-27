package models

import play.api.data.Form
import play.api.data.Forms._

case class Place(placeType: String, placeName: String, address: String, contact: String, description: String, discount: String, picture: String){
}

object Place {

  val placesForm: Form[Place] = Form(
    mapping(
      "placeType" -> nonEmptyText,
      "placeName" -> nonEmptyText,
      "address" -> nonEmptyText,
      "contact" -> nonEmptyText,
      "description" -> nonEmptyText,
        "discount" -> nonEmptyText,
        "picture" -> nonEmptyText
    ) (Place.apply)(Place.unapply)
  )

}
