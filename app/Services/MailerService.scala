package Services

import play.api.libs.mailer._
import javax.inject.Inject

class MailerService @Inject() (mailerClient: MailerClient) {

  def sendEmail(email: Email) = {
    mailerClient.send(email)
  }

}