package controllers

import Services.MongoService
import javax.inject.Inject
import models.JsonFormats._
import models.Mail
import play.api.libs.json.Json
import play.api.mvc._
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

class EmailController @Inject()(components: ControllerComponents,
                                val mongoService: MongoService
                                     ) extends AbstractController(components)
   with play.api.i18n.I18nSupport {

  implicit val ec: ExecutionContext = components.executionContext
  val collection: Future[JSONCollection] = mongoService.mailCollection

  def email: Action[AnyContent] = Action{ implicit request: Request[AnyContent]=>
    Ok(views.html.email(Mail.mailForm))
  }


  def submit: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    Mail.mailForm.bindFromRequest.fold({
      formWithErrors =>
        Future.successful(BadRequest(views.html.email(formWithErrors)))
    }, {
      mail =>
        val cursor: Future[Cursor[Mail]] = collection.map {
          _.find(Json.obj("contactable" -> true)).
            sort(Json.obj("created" -> -1)).
            cursor[Mail]()
        }

        val futureUsersList: Future[List[Mail]] =
          cursor.flatMap(
            _.collect[List](
              -1,
              Cursor.FailOnError[List[Mail]]()
            )
          )

        futureUsersList.map {
          value =>
            if (value.headOption.nonEmpty) {
              collection.flatMap(_.insert.one(mail)).map {
                _ => Ok("email sent")
              }
              Ok("mail sent")
            }
            else BadRequest("No Emails to send to")
        }
    })
  }
  def findAllMail(): Future[List[Mail]] = {
    collection.map {
      _.find(Json.obj("contactable" -> true))
        .sort(Json.obj("created" -> -1))
        .cursor[Mail]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[Mail]]()
      )
    )
  }

  def getAllContactableEmails: Action[AnyContent] = Action.async {
    findAllMail().map {
      mailList => Ok("Places: " + mailList.toString())


    }
  }


}
