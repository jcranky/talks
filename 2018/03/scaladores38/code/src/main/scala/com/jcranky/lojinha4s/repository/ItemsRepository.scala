package com.jcranky.lojinha4s.repository

import cats.data.EitherT
import cats.effect.Async
import com.jcranky.lojinha4s.model.Item

trait ItemsRepository[F[_]] {

  def findAllItems(implicit F: Async[F]): EitherT[F, RepositoryError, List[Item]]

}
