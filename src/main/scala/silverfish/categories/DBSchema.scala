package silverfish.categories

import slick.jdbc.H2Profile.api._

import scala.language.postfixOps

object DBSchema {
  class CategoriesTable(tag: Tag) extends Table[Category](tag, "Categories") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def categoryId = column[Int]("CATEGORY_ID")

    def * = (id, name, categoryId) <> (Category.tupled, Category.unapply)
  }

  val Categories = TableQuery[CategoriesTable]
}
