package simulations

import configs.Parameters.baseUrl
import io.gatling.core.Predef.{Simulation, constantConcurrentUsers, rampConcurrentUsers}
import io.gatling.http.Predef.http
import io.gatling.http.protocol._
import io.gatling.core.Predef._
import scenarios.RegressScenario.RegressScenario

import scala.concurrent.duration.DurationInt

/** Represents a simulation of loadtest */
class RegressSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,application/json,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .disableFollowRedirect

  before {
    println("Going to run regress-scenario")
  }

  setUp(
    RegressScenario.inject(
      atOnceUsers(1)
    ).protocols(httpProtocol),
  ).maxDuration(10.minutes)

}
