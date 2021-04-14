package silverfish.categories

import models.Identifiable

final case class Category(id: Int, name: String, categoryId: Int)
    extends Identifiable
