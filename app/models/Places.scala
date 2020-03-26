package models

import play.api.data.Form
import play.api.data.Forms._

case class Places(placeName: String, address: String, contact: String, description: String, discount: String, picture: String){
}

object Places {

  val placesForm: Form[Places] = Form(
    mapping(
      "placeName" -> nonEmptyText,
      "address" -> nonEmptyText,
      "contact" -> nonEmptyText,
      "description" -> nonEmptyText,
        "discount" -> nonEmptyText,
        "picture" -> nonEmptyText
    ) (Places.apply)(Places.unapply)
  )

}






