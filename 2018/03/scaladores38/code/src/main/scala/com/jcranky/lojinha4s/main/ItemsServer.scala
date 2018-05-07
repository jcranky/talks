package com.jcranky.lojinha4s.main

import cats.effect.{Effect, IO}
import com.jcranky.lojinha4s.repository.InMemoryItemsRepository
import com.jcranky.lojinha4s.services.{ItemsService, ProxyService}
import fs2.StreamApp
import org.http4s.client.Client
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object ItemsServerApp extends ItemsServer[IO]

class ItemsServer[F[_]: Effect] extends StreamApp[F] {

  val itemsRepository = new InMemoryItemsRepository[F]()
  val itemsService = new ItemsService[F](itemsRepository)

  def stream(args: List[String], requestShutdown: F[Unit]) =
    BlazeBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .mountService(itemsService.service, "/")
      .serve
}
