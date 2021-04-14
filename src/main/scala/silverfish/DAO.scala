package silverfish

import silverfish.bookmarks.{Dao => BookmarksDao}
import silverfish.categories.{Dao => CategoriesDao}
import slick.jdbc.H2Profile.api._

class DAO(db: Database) {
  val bookmarksDao = new BookmarksDao(db)
  val categoriesDao = new CategoriesDao(db)
}
