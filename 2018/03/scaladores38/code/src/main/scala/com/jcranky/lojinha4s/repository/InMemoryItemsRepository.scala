package com.jcranky.lojinha4s.repository

import cats.data.EitherT
import cats.effect.Async
import com.jcranky.lojinha4s.model.Item

class InMemoryItemsRepository[F[_]] extends ItemsRepository[F] {

  override def findAllItems(implicit F: Async[F]): EitherT[F, RepositoryError, List[Item]] =
    EitherT.rightT(
      List(
        Item(1, "item1"),
        Item(2, "item2"),
        Item(3, "item3")
      )
    )
}
