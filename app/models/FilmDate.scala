package models

case class FilmDate(date: Tuple3[Int,Int,Int], time: Tuple2[Int,Int] )

object FilmDate{

  def apply(DD: Int, MM: Int, YYYY: Int, Hour:Int, Min:Int) = new FilmDate(Tuple3(DD,MM,YYYY),Tuple2(Hour, Min))

}