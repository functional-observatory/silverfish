package silverfish.categories

import sangria.execution.deferred.Fetcher
import sangria.macros.derive.{Interfaces, deriveObjectType}
import sangria.schema.ObjectType
import silverfish.GraphqlContext
import silverfish.graphql.IdentifiableType.Identifiable

object GraphqlSchema {
  lazy val CategoryType: ObjectType[Unit, Category] =
    deriveObjectType[Unit, Category](
      Interfaces(Identifiable)
    )

  val categoriesFetcher: Fetcher[GraphqlContext, Category, Category, Int] =
    Fetcher((ctx: GraphqlContext, ids: Seq[Int]) =>
      ctx.dao.categoriesDao.categories(ids)
    )
}
