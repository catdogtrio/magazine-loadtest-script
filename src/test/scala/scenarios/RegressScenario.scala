package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import requests.MagazineApiRequests._
import scenarios.content.AddContentScenario._
import scenarios.login.LoginScenario._

/** Represents a regress scenario of loadtest */
object RegressScenario {
  var RegressScenario: ScenarioBuilder = scenario("RegressScenario")

    .exec(getEn)
    .exec(LoginScenario)

    .doIf(session => session("login_status_code").as[Integer] == 200) {
      exec(getEn)
        .exitBlockOnFail(
          exec(AddArticleScenario)
        )
    }

}
