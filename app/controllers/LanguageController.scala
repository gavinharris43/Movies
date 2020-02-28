package controllers

import com.google.inject.Inject
import play.api.i18n.{I18nSupport, Lang, Langs}
import play.api.mvc.{AbstractController, ControllerComponents}

class LanguageController @Inject()(component: ControllerComponents, langs: Langs)
  extends AbstractController(component)
  with I18nSupport {

  def homePageInWelsh = Action {
    Redirect("/").withLang(Lang("cy"))
  }

  def homePageInEnglish = Action {
    Redirect("/").withoutLang
  }

}
