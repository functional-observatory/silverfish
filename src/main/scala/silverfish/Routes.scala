package silverfish

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import models._

object Routes {
  def route(dao: DAO): Route = {
    concat(
      get {
        path("bookmarks") {
          complete(dao.allBookmarks)
        }
      },
      get {
        path("categories") {
          complete(dao.allCategories)
        }
      },
      get {
        complete(StatusCodes.NotFound)
      }
    )
  }
}
