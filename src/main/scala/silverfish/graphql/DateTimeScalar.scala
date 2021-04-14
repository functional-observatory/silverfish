package silverfish.graphql

import akka.http.scaladsl.model.DateTime
import models.DateTimeCoerceViolation
import sangria.ast.StringValue
import sangria.schema.ScalarType

object DateTimeScalar {
  val GraphqlDateTime: ScalarType[DateTime] = ScalarType[DateTime](
    "DateTime",
    coerceOutput = (dt, _) => dt.toString,
    coerceInput = {
      case StringValue(dt, _, _, _, _) =>
        DateTime.fromIsoDateTimeString(dt).toRight(DateTimeCoerceViolation)
      case _ => Left(DateTimeCoerceViolation)
    },
    coerceUserInput = {
      case s: String =>
        DateTime.fromIsoDateTimeString(s).toRight(DateTimeCoerceViolation)
      case _ => Left(DateTimeCoerceViolation)
    }
  )
}
