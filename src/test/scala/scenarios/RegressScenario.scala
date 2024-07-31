package scenarios

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import requests.MagazineAuthRequests._
import scenarios.content.AddContentScenario._
import scenarios.login.LoginScenario._
import scenarios.login.LogoutScenario._

/** Represents a regress scenario of loadtest */
object RegressScenario {
  var RegressScenario: ScenarioBuilder = scenario("RegressScenario")
    .exec(LoginScenario)
    .doIf(session => session("login_status_code").as[Integer] == 200) {
      exec(getEn)
        .exec(AddArticleScenario)
        .exec(LogoutScenario)
    }

}
