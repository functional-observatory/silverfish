package silverfish.graphql

import models.Identifiable
import sangria.schema.{Field, IntType, InterfaceType, fields}

object IdentifiableType {
  val Identifiable: InterfaceType[Unit, Identifiable] =
    InterfaceType(
      "Identifiable",
      fields[Unit, Identifiable](
        Field("id", IntType, resolve = _.value.id)
      )
    )
}
