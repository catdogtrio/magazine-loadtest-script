package scenarios.login

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import requests.MagazineApiRequests._

object LoginScenario {
  private val users = csv("users.csv").random

  var LoginScenario: ScenarioBuilder = scenario("login-scenario")
    .feed(users)
    .exec(getEnUserLogin)
    .exec(postEnUserLogin)
    .exec(getEnUserUserId)
}
