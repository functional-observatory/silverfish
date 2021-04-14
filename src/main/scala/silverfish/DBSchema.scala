package silverfish

import models._
import akka.http.scaladsl.model.DateTime
import slick.ast.BaseTypedType

import scala.concurrent.Await
import scala.concurrent.duration._
import java.sql.Timestamp
import scala.language.postfixOps
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcType

object DBSchema {
  implicit val dateTimeColumnType
      : JdbcType[DateTime] with BaseTypedType[DateTime] =
    MappedColumnType.base[DateTime, Timestamp](
      dt => new Timestamp(dt.clicks),
      ts => DateTime(ts.getTime)
    )

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
  }

  class CategoriesTable(tag: Tag) extends Table[Category](tag, "Categories") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def categoryId = column[Int]("CATEGORY_ID")

    def * = (id, name, categoryId) <> (Category.tupled, Category.unapply)
  }

  val Categories = TableQuery[CategoriesTable]
  val Bookmarks = TableQuery[BookmarksTable]

  val databaseSetup = DBIO.seq(
    Categories.schema.create,
    Bookmarks.schema.create
  )

  def createDatabase: DAO = {
    val db = Database.forConfig("h2mem")

    Await.result(db.run(databaseSetup), 10 seconds)
    new DAO(db)
  }
}
