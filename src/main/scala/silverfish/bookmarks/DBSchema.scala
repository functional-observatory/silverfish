package silverfish.bookmarks

import akka.http.scaladsl.model.DateTime
import silverfish.categories.DBSchema.Categories
import slick.jdbc.H2Profile.api._

import scala.language.postfixOps

object DBSchema {
  import silverfish.DBSchema._

  class BookmarksTable(tag: Tag) extends Table[Bookmark](tag, "Bookmarks") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def link = column[String]("LINK")
    def categoryId = column[Int]("CATEGORY_ID")
    def timestamp = column[DateTime]("TIMESTAMP")

    def * =
      (
        id,
        name,
        link,
        categoryId,
        timestamp
      ) <> (Bookmark.tupled, Bookmark.unapply)

    def category = foreignKey("category_fk", categoryId, Categories)(_.id)
  }

  val Bookmarks = TableQuery[BookmarksTable]
}
