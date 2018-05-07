package com.jcranky.lojinha4s.services

import cats.{Applicative, Monad}
import org.http4s.client.Client
import org.http4s.{HttpService, Request, Response, Uri}
import org.http4s.dsl.Http4sDsl

class ProxyService[F[_] : Applicative : Monad](client: Client[F]) extends Http4sDsl[F] {

  val googleUri: Uri = Uri.uri("https://www.google.com.br/search?q=http4s")

  val service: HttpService[F] = HttpService[F] {
    case GET -> Root / "google" => proxy(googleUri)
  }

  def proxy(uri: Uri): F[Response[F]] = {
    val req = Request[F](GET, uri)
    client.toHttpService.run(req).getOrElseF(NotFound())
  }
}
