package silverfish

import models._
import DBSchema.{Bookmarks, Categories}
import slick.jdbc.H2Profile.api._
import scala.concurrent.Future

class DAO(db: Database) {
  def allBookmarks: Future[Seq[Bookmark]] = db.run(Bookmarks.result)
  def allCategories: Future[Seq[Category]] = db.run(Categories.result)
}
