package silverfish.bookmarks

import akka.http.scaladsl.model.DateTime
import models.Identifiable

final case class Bookmark(
    id: Int,
    name: String,
    link: String,
    categoryId: Int,
    timestamp: DateTime
) extends Identifiable
