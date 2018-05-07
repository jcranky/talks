package com.jcranky.lojinha4s.services

import cats.effect.Effect
import cats.syntax.flatMap._
import com.jcranky.lojinha4s.repository.ItemsRepository
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

class ItemsService[F[_] : Effect](itemsRepository: ItemsRepository[F]) extends Http4sDsl[F] {

  val service: HttpService[F] = HttpService[F] {
    case GET -> Root / "items" =>
      itemsRepository.findAllItems.value.flatMap {
        case Right(items) => Ok(items)
        case Left(error) => InternalServerError(error.msg)
      }
  }
}
