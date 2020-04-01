package helpers

import models.Movies
import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.DateTimeFormat


class FilmDateControl{

  private val DATE_TIME_PATTERN = DateTimeFormat.forPattern("dd/MM/yyyy")

  def getDate(string: String): String ={
    string.substring(0,10)
  }
  def sortByDateTime(film: Movies, film2: Movies): Boolean ={
    DATE_TIME_PATTERN
    val date1 = DATE_TIME_PATTERN.parseDateTime(getDate(film.dateRelease))
    val date2 = DATE_TIME_PATTERN.parseDateTime(getDate(film2.dateRelease))
    if (date1.isAfter(date2)) true else false
  }
}