package models

import play.api.data.Form
import play.api.data.Forms._


case class Subscribe(email: String, contactable: Boolean){
}

object Subscribe{
  val subscribeForm: Form[Subscribe] = Form(
    mapping(
      "email" -> nonEmptyText,
      "contactable" -> boolean
    )(Subscribe.apply)(Subscribe.unapply)
  )

}




