package models

import play.api.data.Form
import play.api.data.Forms._

case class LoginDetails(username: String, password: String)

object LoginDetails {

  val userList: List[LoginDetails] = List(LoginDetails("admin", "password"))

  val loginForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginDetails.apply)(LoginDetails.unapply)
  )

  def checkIfUserIsVali(userDetails: LoginDetails) = userList.contains(userDetails)

  def getUsername(username: String) = userList.filter(user => user.username == username).headOption


}
