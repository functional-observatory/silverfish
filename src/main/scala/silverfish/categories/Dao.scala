package silverfish.categories

import silverfish.categories.DBSchema.Categories
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

class Dao(db: Database) {
  def allCategories: Future[Seq[Category]] = db.run(Categories.result)
  def categories(ids: Seq[Int]): Future[Seq[Category]] =
    db.run(Categories.filter(_.id inSet ids).result)
}
