package models

import play.api.data.Form
import play.api.data.Forms._

case class Search(searchTerm: String)

object  Search{

  val searchForm: Form[Search] = Form(
    mapping(
      "searchTerm" -> nonEmptyText
    )(Search.apply)(Search.unapply)
  )
}