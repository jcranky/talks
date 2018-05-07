package com.jcranky.lojinha4s.model

import cats.Applicative
import io.circe.Encoder
import io.circe.derivation._
import org.http4s.EntityEncoder
import org.http4s.circe._

case class Item(id: Int, name: String)

object Item {

  implicit val encoder: Encoder[Item] =
    deriveEncoder(renaming.snakeCase)

  implicit def entityEncoder[F[_]: Applicative]: EntityEncoder[F, Item] =
    jsonEncoderOf[F, Item]

  implicit def entityEncoderL[F[_]: Applicative, A: Encoder]: EntityEncoder[F, List[A]] =
    jsonEncoderOf[F, List[A]]
}
