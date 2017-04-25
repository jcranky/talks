package jcranky

import cats.Monoid
import de.zalando.brand.quality.api.endpoint.Results.GetResult
import de.zalando.brand.quality.api.json.{Links, MerchantsEntityJson}
import de.zalando.brand.quality.domain._
import de.zalando.brand.quality.main.{ApplicationConfig, Environment, EnvironmentReadError}
import de.zalando.brand.quality.repository.DetailsRepository
import de.zalando.brand.quality.service.{BusinessPartnerService, MasterData}
import de.zalando.brand.quality.service.ServiceErrorEffect.ServiceError
import de.zalando.brand.quality.tinbox.build.BuildInfo
import org.atnos.eff.{Eff, ExecutorServices, Fx}
import org.atnos.eff.addon.scalaz.concurrent.TimedTask
import org.atnos.eff.addon.scalaz.task._Task
import org.http4s.{EntityEncoder, Uri}
import org.http4s.headers.Authorization
import org.http4s.rho.bits.ResponseGeneratorInstances.Ok
import org.specs2.{ScalaCheck, Specification}
import org.zalando.client.AuthenticationClient
import org.zalando.control.ScalazAsyncTaskService
import org.zalando.service.FlowIdEffect._flowId
import org.zalando.service.Logged
import org.zalando.service.LoggerEffect._

import scalaz.concurrent.Task

object Samples {

  import org.zalando.service.LoggerEffect

  // -----
  type GetHealthResult = GetResult[String]
  val config: ApplicationConfig = ApplicationConfig.test

  def runGet: Task[GetHealthResult] =
    Ok(List("Alive",
      s"Environment ${config.environment.value}",
      s"Version     ${BuildInfo.version}"
    ).mkString("\n"))

  // -----
  object Endpoints {
    def authenticate[A](authenticationClient: AuthenticationClient,
                        authorization:        Authorization)
                       (action: => Task[A]): Task[A] = ???


    def runServiceError[A](action: => Task[A]) = ???

    // e mais um monte de coisas
  }

  // -----
  import Endpoints._
  type GetMerchantsResult = GetResult[MerchantsEntityJson]
  val authenticationClient: AuthenticationClient = ???
  val partnerService: BusinessPartnerService = ???

  def debug[A](msg: String): Task[A] = ???

  object Responses {
    def okGet[A](entity: A)(implicit w: EntityEncoder[A]): Task[GetResult[A]] =
      ???
  }
  import Responses._

  def runGet(authorization: Authorization, requestUri: Uri): Task[GetMerchantsResult] =
    authenticate(authenticationClient, authorization) {
      for {
        _         <- debug("retrieving a list of merchants")
        merchants <- partnerService.getAllMerchants
        r         <- okGet[MerchantsEntityJson](
          MerchantsEntityJson.fromMerchants(merchants, Links.fromString("/merchants")))
      } yield r
    }

  // -----
  object Environment {
    def fromProperties(props: Map[String, String]):
    Either[EnvironmentReadError, Environment] = ???
  }

  // -----
  lazy val dbHost     = "DB_HOST"
  lazy val dbPort     = "DB_PORT"
  lazy val dbName     = "DB_NAME"
  lazy val dbUser     = "DB_USER"
  lazy val dbPassword = "DB_PASSWORD"

  class EnvSpec extends Specification { def is = ""
    def missingVariable = {
      val env = Environment.fromProperties(Map.empty)
      env must be left EnvironmentReadError(
        List(s"$dbHost not found",
          s"$dbPort not found",
          s"$dbName not found",
          s"$dbUser not found",
          s"$dbPassword not found"))
    }
  }

  // -----
  class EnvSpec2 extends Specification with ScalaCheck { def is = ""
    def missingVariable = prop { (props: Map[String, String]) =>
      val env = Environment.fromProperties(props)
      env must be left EnvironmentReadError(
        List(s"$dbHost not found",
          s"$dbPort not found",
          s"$dbName not found",
          s"$dbUser not found",
          s"$dbPassword not found"))
    }
  }

  // -----
  def getReview(reviewId: ReviewId): Option[Review] =
    ???

  // -----
  def fromProperties(props: Map[String, String]):
  Either[EnvironmentReadError, Environment] = ???

  // -----
  case class PollResults(successes: Int, failures: Int)

  // -----
  import cats.syntax.monoid._

  val r1 = PollResults(1, 0)
  val r2 = PollResults(0, 1)

  (r1 |+| r2) == PollResults(1, 1)

  // -----
  import cats.instances.list._
  import cats.syntax.foldable._

  val r3 = PollResults(1, 1)

  List(r1, r2, r3).combineAll == PollResults(2, 2)


  // -----
  object PollResults {
    implicit val monoid: Monoid[PollResults] =
      new Monoid[PollResults] {
        def empty: PollResults =
          PollResults(0, 0)

        def combine(x: PollResults, y: PollResults): PollResults =
          PollResults(x.successes + y.successes, x.failures + y.failures)
      }
  }

  // -----
  List(
    "1eed347b-6ac2-4e6f-a456-6435efde6500",
    "7ca9f970-14ea-4b3c-a1c3-9eca05952246",
    "e18e458a-de38-40ee-8119-4130eed7486a",
    "b10c3d91-f6af-4bdc-aa12-572bd172507f",
    "15e9f89d-bdea-4033-a828-296ecf960517"
  ).map(MerchantId.apply)

  // -----
  case class Application(env: Environment)

  val props: Map[String, String] = Map.empty
  val eitherEnv: Either[EnvironmentReadError, Environment] =
    Environment.fromProperties(props)

  val eitherApp: Either[EnvironmentReadError, Application] =
    eitherEnv.map { env =>
      Application(env)
    }

  // -----
  case class Credentials(name: String, password: String)
  def readUsername(): Option[String] = ???
  def readPassword(): Option[String] = ???

  val usernameOpt: Option[String] = readUsername()
  val userPassOpt: Option[String] = readPassword()

  val credentialsOpt: Option[Credentials] =
    usernameOpt.flatMap { username =>
      userPassOpt.map(password => Credentials(username, password))
    }

  // -----
  for {
    username <- usernameOpt
    password <- userPassOpt
  } yield Credentials(username, password)

  // -----
  type S = Fx.fx3[TimedTask, ServiceError, Logged]

  val detailsRepository: DetailsRepository = ???
  val masterData: MasterData = ???
  val modelId = ModelId("id")

  private def resolveSizeDescription[R :_Task :_logged :_flowId]
  (codeDescription: SizeChartCodeDescription): Eff[R, SizeChartCodeDescription] = ???

  import org.atnos.eff.syntax.all._

  val getModels: Eff[S, ModelDetails] = for {
    model        <- detailsRepository.findModelDetails[S](modelId).collapse
    brandName    <- masterData.getBrandName[S](model.brandCode)
    descriptions <- model.sizeCharts.traverseA(resolveSizeDescription[S])
  } yield model.copy(brandName  = brandName, sizeCharts = descriptions)

  // -----
  //  val models =
  //    getModels.run

  // -----
  import de.zalando.brand.quality.service.ServiceErrorEffect._
  val exec: ScalazAsyncTaskService = ???
  import exec._

  val models =
    getModels
      .printLogs
      .runServiceErrorAsync
      .runAsync
      .unsafePerformSync
}
