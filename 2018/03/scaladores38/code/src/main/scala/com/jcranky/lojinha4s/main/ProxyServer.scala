package com.jcranky.lojinha4s.main

import cats.effect.{Effect, IO}
import com.jcranky.lojinha4s.services.ProxyService
import fs2.StreamApp.ExitCode
import fs2.{Stream, StreamApp}
import org.http4s.client.Client
import org.http4s.client.blaze.Http1Client
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object ProxyServerApp extends ProxyServer[IO]

class ProxyServer[F[_]: Effect] extends StreamApp[F] {

  def proxyService(client: Client[F]) =
    new ProxyService[F](client)

  def server(client: Client[F]) =
    BlazeBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .mountService(proxyService(client).service, "/")
      .serve

  def stream(args: List[String], requestShutdown: F[Unit]): Stream[F, ExitCode] =
    for {
      client <- Http1Client.stream[F]()
      exitCode <- server(client)
    } yield exitCode
}
