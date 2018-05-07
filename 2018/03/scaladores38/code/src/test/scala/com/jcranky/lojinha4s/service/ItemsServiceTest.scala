package com.jcranky.lojinha4s.service

import cats.effect.IO
import cats.instances.string._
import cats.syntax.eq._
import com.jcranky.lojinha4s.fakes.ItemsRepositoryFake
import com.jcranky.lojinha4s.repository.ItemsRepository
import com.jcranky.lojinha4s.services.ItemsService
import org.http4s.dsl.io._
import org.http4s.{Method, Request, Response, Uri}
import utest._

object ItemsServiceTest extends TestSuite {

  val tests = Tests {
    "get /items" - {
      def getItems(itemsRepository: ItemsRepository[IO]): Response[IO] = {
        val request = Request[IO](Method.GET, Uri.uri("/items"))
        new ItemsService[IO](itemsRepository).service.orNotFound(request).unsafeRunSync()
      }

      "get an empty items list" - {
        val response = getItems(ItemsRepositoryFake.emptyItemsRepository)

        "200" - assert(response.status === Ok)
        "empty list in the response body" - {
          val body = materializeBody(response)
          assert(body === "[]")
        }
      }

      "get all the items" - {
        val response = getItems(ItemsRepositoryFake.twoItemsRepository)

        "200" - assert(response.status === Ok)
        "two items in the response body" - {
          val body = materializeBody(response)
          assert(body === """[{"id":1,"name":"item1"},{"id":2,"name":"item2"}]""")
        }
      }
    }
  }

  def materializeBody(response: Response[IO]): String =
    response.bodyAsText.compile.toVector.unsafeRunSync().head
}
