import cats.Show
import cats.instances.int._
import cats.instances.string._
import cats.syntax.show._

object MeetCats {
  def main(args: Array[String]): Unit = {
    val showInt = Show.apply[Int]
    val showString = Show.apply[String]

    println(showInt.show(123))
    println(123.show) // cats.syntax.show._ nie potrzeba innych importów

    println(showString.show("asd"))
    println("asd".show)
  }
}
