package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import io.gatling.http.protocol._
import scenarios.RegressScenario.regressScenario
import utils.Parameters.baseUrl
import utils.UsersGeneration.generate

import scala.concurrent.duration.DurationInt

/** Represents a simulation of loadtest */
class ReliabilitySimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,application/json,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .disableFollowRedirect

  before {
    println("Going to run ReliabilitySimulation")
    generate()
  }

  setUp(
    regressScenario(50,70, 24.hours).inject(
      constantConcurrentUsers(1) during(24.hours),
    ).protocols(httpProtocol),
  ).maxDuration(24.hours)

}
