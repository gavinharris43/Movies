package models

import play.api.data.Form
import play.api.data.Forms._



case class EMail(email: String, subject: String, body: String){
}

object EMail {

  val mailForm: Form[EMail] = Form(
    mapping(
      "email" -> email,
      "subject" -> nonEmptyText,
      "body" -> nonEmptyText
    )(EMail.apply)(EMail.unapply)
  )
}


