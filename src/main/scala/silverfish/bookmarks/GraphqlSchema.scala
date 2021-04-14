package silverfish.bookmarks

import sangria.execution.deferred.Fetcher
import sangria.macros.derive.{Interfaces, ReplaceField, deriveObjectType}
import sangria.schema.{
  Argument,
  Field,
  IntType,
  ListInputType,
  ListType,
  OptionType,
  fields
}
import silverfish.GraphqlContext
import silverfish.categories.GraphqlSchema.{CategoryType, categoriesFetcher}
import silverfish.graphql.DateTimeScalar.GraphqlDateTime
import silverfish.graphql.IdentifiableType.Identifiable

object GraphqlSchema {
  private val Id = Argument("id", IntType)
  private val Ids = Argument("ids", ListInputType(IntType))

  private lazy val BookmarkType =
    deriveObjectType[Unit, Bookmark](
      Interfaces(Identifiable),
      ReplaceField(
        "timestamp",
        Field("timestamp", GraphqlDateTime, resolve = _.value.timestamp)
      ),
      ReplaceField(
        "categoryId",
        Field(
          "category",
          CategoryType,
          resolve = c => categoriesFetcher.defer(c.value.categoryId)
        )
      )
    )

  private val bookmarksFetcher =
    Fetcher((ctx: GraphqlContext, ids: Seq[Int]) =>
      ctx.dao.bookmarksDao.bookmarks(ids)
    )

  val bookmarkFields: Seq[Field[GraphqlContext, Unit]] =
    fields[GraphqlContext, Unit](
      Field(
        "allBookmarks",
        ListType(BookmarkType),
        resolve = c => c.ctx.dao.bookmarksDao.allBookmarks
      ),
      Field(
        "bookmark",
        OptionType(BookmarkType),
        arguments = List(Id),
        resolve = c => bookmarksFetcher.deferOpt(c.arg[Int]("id"))
      ),
      Field(
        "bookmarks",
        ListType(BookmarkType),
        arguments = List(Ids),
        resolve = c => bookmarksFetcher.deferSeq(c.arg[Seq[Int]]("ids"))
      )
    )
}
