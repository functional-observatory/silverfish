package silverfish.bookmarks

import silverfish.bookmarks.DBSchema.Bookmarks
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

class Dao(db: Database) {
  def allBookmarks: Future[Seq[Bookmark]] =
    db.run(Bookmarks.result)
  def bookmarks(ids: Seq[Int]): Future[Seq[Bookmark]] =
    db.run(Bookmarks.filter(_.id inSet ids).result)
}
