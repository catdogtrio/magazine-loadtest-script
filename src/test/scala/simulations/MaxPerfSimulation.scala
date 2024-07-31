package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import io.gatling.http.protocol._
import scenarios.RegressScenario.regressScenario
import utils.Parameters.baseUrl
import utils.UsersGeneration.generate

import scala.concurrent.duration.DurationInt

/** Represents a simulation of loadtest */
class MaxPerfSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,application/json,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .disableFollowRedirect

  before {
    println("Going to run MaxPerfSimulation")
    generate()
  }

  setUp(
    regressScenario(50,70, 65.minutes).inject(
      incrementConcurrentUsers(100)
        .times(5)
        .eachLevelLasting(10.minutes)
        .separatedByRampsLasting(1.minute)
        .startingFrom(100)

        /* i used to do it like this:
      rampConcurrentUsers(0) to 100 during (5.minutes),
      constantConcurrentUsers(100) during(25.minutes),
      rampConcurrentUsers(100) to 200 during (5.minutes),
      constantConcurrentUsers(200) during(25.minutes),
      rampConcurrentUsers(200) to 300 during (5.minutes),
      constantConcurrentUsers(300) during(25.minutes),
      rampConcurrentUsers(300) to 400 during (5.minutes),
      constantConcurrentUsers(400) during(25.minutes),*/
    ).protocols(httpProtocol),
  ).maxDuration(65.minutes)

}
