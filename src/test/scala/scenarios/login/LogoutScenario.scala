package scenarios.login

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import requests.MagazineUserRequests._

object LogoutScenario {
  var LogoutScenario: ScenarioBuilder = scenario("logout-scenario")
    .group("Logout") {
      exec(getEnUserLogout)
    }
}
