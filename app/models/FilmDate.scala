package models

case class FilmDate(date: Tuple3[Int,Int,Int], time: Tuple2[Int,Int] ){
  override def toString: String = {
    if ((date._1 < 9) && (date._2 < 9)){ (("0"+ date._1+ "/" + "0" + date._2 + "/" + date._3 : String) + time) :String}
    else if ((date._1 < 9) && (date._2 > 9)){ (("0"+ date._1+ "/" + date._2 + "/" + date._3 : String) + time) :String}
    else if ((date._1 > 9) && (date._2 < 9)){ ((date._1+ "/" + "0" + date._2 + "/" + date._3 : String) + time) :String}
    else{ ("" + date._1 + "/" + date._2 + "/" + date._3 + time :String)}
  }
}

object FilmDate{

  def apply(DD: Int, MM: Int, YYYY: Int, Hour:Int, Min:Int) = new FilmDate(Tuple3(DD,MM,YYYY),Tuple2(Hour, Min))


}

