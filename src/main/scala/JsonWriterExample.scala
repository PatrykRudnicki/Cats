sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
case object JsNull extends Json

trait JsonWriter[A] {
  def write(value: A): Json
}

final case class Person(name: String, email: String)

// Below we need to create implicit vals for every type
// If we want to have Option[A] we need to create specific implicit for Option[String] or Option[Int]
object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      def write(value: String): Json =
        JsString(value)
    }

  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      def write(value: Person): Json =
        JsObject(Map(
          "name" -> JsString(value.name),
          "email" -> JsString(value.email)
        ))
    }

  // Below implicit writer for all Options
  implicit def optionWriter[A](implicit writer: JsonWriter[A]) =
    new JsonWriter[Option[A]] {
      def write(option: Option[A]): Json =
        option match {
          case Some(aValue) => writer.write(aValue)
          case None => JsNull
        }
    }

  // etc...
}

object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}

object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json =
      w.write(value)
  }
}

object Main {
  import JsonWriterInstances._
  import JsonSyntax._

  //def main(args: Array[String]): Unit = {
    implicitly[JsonWriter[String]]

    // implicit writer
    println(Json.toJson(Person("Dave", "dave@example.com")))

    // explicit writer
    println(Json.toJson(Person("Dave", "dave@example.com"))(personWriter))

    // implicit writer
    println(Person("Dave", "dave@example.com").toJson)

    // explicit writer
    println(Person("Dave", "dave@example.com").toJson(personWriter))

    println(Json.toJson(Option("A string")))
    println(Json.toJson(Option("A string"))(optionWriter[String]))


    // Below doesn't work
    //println(Json.toJson(Option(1)))
    //println(Json.toJson(Option(10.0)))
  //}
}