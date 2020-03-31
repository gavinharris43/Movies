package models

import play.api.data.Form
import play.api.data.Forms._



case class Mail(email: String, subject: String, body: String){
}

object Mail {

  val mailForm: Form[Mail] = Form(
    mapping(
      "email" -> email,
      "subject" -> nonEmptyText,
      "body" -> nonEmptyText
    )(Mail.apply)(Mail.unapply)
  )
}


