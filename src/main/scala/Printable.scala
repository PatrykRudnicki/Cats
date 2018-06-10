trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val printableString: Printable[String] =
    new Printable[String] {
      def format(value: String): String = value
    }

  implicit val printableInt: Printable[Int] =
    new Printable[Int] {
      def format(value: Int): String = value.toString
    }

  implicit val printableCat: Printable[Cat] =
    new Printable[Cat] {
      def format(value: Cat): String = {
        val name = Printable.format(value.name)
        val age = Printable.format(value.age)
        val color = Printable.format(value.color)
        s"$name is a $age year-old $color cat."
      }
    }
}

object Printable {
  def format[A](input: A)(implicit p: Printable[A]): String =
    p.format(input)

  def print[A](input: A)(implicit p: Printable[A]): Unit =
    println(p.format(input))
}

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String = p.format(value)

    def print(implicit p: Printable[A]): Unit = println(p.format(value))
  }
}

final case class Cat(name: String, age: Int, color: String)

object JsonWriterExercise {
  import PrintableInstances._
  import PrintableSyntax._

//  def main(args: Array[String]): Unit = {
//    val cat = Cat("Garfield", 32, "Ginger")
//
//    Printable.print(cat)
//
//    cat.print
//  }
}