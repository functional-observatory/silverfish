package silverfish

import sangria.execution.deferred.DeferredResolver
import sangria.schema.{ObjectType, _}
import silverfish.bookmarks.GraphqlSchema.bookmarkFields
import silverfish.categories.GraphqlSchema.categoriesFetcher

object GraphqlSchema {
  val Resolver: DeferredResolver[GraphqlContext] =
    DeferredResolver.fetchers(categoriesFetcher)

  private val QueryType = ObjectType(
    "Query",
    bookmarkFields.toList
  )

  val SchemaDefinition: Schema[GraphqlContext, Unit] = Schema(QueryType)
}
