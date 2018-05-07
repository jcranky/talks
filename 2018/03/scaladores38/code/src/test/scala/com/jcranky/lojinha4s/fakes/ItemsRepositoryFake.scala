package com.jcranky.lojinha4s.fakes

import cats.data.EitherT
import cats.effect.{Async, IO}
import com.jcranky.lojinha4s.model.Item
import com.jcranky.lojinha4s.repository.{ItemsRepository, RepositoryError}

object ItemsRepositoryFake {

  def emptyItemsRepository = new ItemsRepository[IO] {

    override def findAllItems(implicit F: Async[IO]): EitherT[IO, RepositoryError, List[Item]] =
      EitherT.rightT(List.empty)
  }

  def twoItemsRepository = new ItemsRepository[IO] {

    override def findAllItems(implicit F: Async[IO]): EitherT[IO, RepositoryError, List[Item]] =
      EitherT.rightT(
        List(
          Item(1, "item1"),
          Item(2, "item2")
        )
      )
  }
}
