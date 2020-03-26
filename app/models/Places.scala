package models

import play.api.data.Form
import play.api.data.Forms._

case class Places(placeType: String,placeName: String, address: String, contact: String, description: String, discount: String, picture: String){
}

object Places {

  val placesForm: Form[Places] = Form(
    mapping(
      "placeType" -> nonEmptyText,
      "placeName" -> nonEmptyText,
      "address" -> nonEmptyText,
      "contact" -> nonEmptyText,
      "description" -> nonEmptyText,
        "discount" -> nonEmptyText,
        "picture" -> nonEmptyText
    ) (Places.apply)(Places.unapply)
  )

}






