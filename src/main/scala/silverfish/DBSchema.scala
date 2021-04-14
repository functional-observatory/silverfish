package silverfish

import akka.http.scaladsl.model.DateTime
import silverfish.bookmarks.Bookmark
import silverfish.bookmarks.DBSchema.Bookmarks
import silverfish.categories.Category
import silverfish.categories.DBSchema.Categories
import slick.ast.BaseTypedType
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcType

import java.sql.Timestamp
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

object DBSchema {
  implicit val dateTimeColumnType
      : JdbcType[DateTime] with BaseTypedType[DateTime] =
    MappedColumnType.base[DateTime, Timestamp](
      dt => new Timestamp(dt.clicks),
      ts => DateTime(ts.getTime)
    )

  val databaseSetup = DBIO.seq(
    Categories.schema.create,
    Bookmarks.schema.create,
    Categories forceInsertAll Seq(
      Category(
        id = 0,
        name = "root",
        categoryId = 0
      ),
      Category(
        id = 1,
        name = "dev",
        categoryId = 0
      )
    ),
    Bookmarks forceInsertAll Seq(
      Bookmark(
        id = 0,
        name = "Github",
        link = "github.com",
        categoryId = 1,
        timestamp = DateTime.now
      )
    )
  )

  def createDatabase: DAO = {
    val db = Database.forConfig("h2mem")

    Await.result(db.run(databaseSetup), 10 seconds)
    new DAO(db)
  }
}
