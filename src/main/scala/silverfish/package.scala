import akka.http.scaladsl.model.DateTime
import spray.json.DefaultJsonProtocol._
import spray.json.{JsString, JsValue, RootJsonFormat}

package object models {
  final case class Bookmark(
      id: Int,
      name: String,
      link: String,
      categoryId: Int,
      timestamp: DateTime
  )
  final case class Category(id: Int, name: String, categoryId: Int)

  implicit object DateJsonFormat extends RootJsonFormat[DateTime] {
    override def write(obj: DateTime): JsString =
      JsString(obj.toIsoDateString())
    override def read(json: JsValue): DateTime =
      json match {
        case JsString(s) =>
          DateTime.fromIsoDateTimeString(s).getOrElse(DateTime.now)
        case _ => throw new Exception("Malformed datetime")
      }
  }
  implicit val bookmarkFormat: RootJsonFormat[Bookmark] = jsonFormat5(Bookmark)
  implicit val categoryFormat: RootJsonFormat[Category] = jsonFormat3(Category)
}
