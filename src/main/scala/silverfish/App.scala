package silverfish

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import scala.util.{Failure, Success}

object App {
  implicit val system: ActorSystem[Nothing] =
    ActorSystem(Behaviors.empty, "Silverfish")
  implicit val executionContext: ExecutionContextExecutor =
    system.executionContext

  private val dao: DAO = DBSchema.createDatabase

  def main(args: Array[String]): Unit = {
    val route = Routes.route(dao)
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    bindingFuture onComplete {
      case Success(value) =>
        println(
          s"Server online at http://localhost:8080/\nPress RETURN to stop..."
        )
        StdIn.readLine()
        bindingFuture
          .flatMap(_.unbind())
          .onComplete(_ => system.terminate())
      case Failure(_) =>
        println("Failed to bind to 8080")
        system.terminate()
    }
  }
}
