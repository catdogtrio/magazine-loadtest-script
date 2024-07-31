package scenarios

import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import requests.MagazineApiRequests._
import scenarios.content.AddContentScenario._
import scenarios.login.LoginScenario._

object RegressScenario {
  var RegressScenario: ScenarioBuilder = scenario("regress-scenario")
    .exec(getEn)
    .exec(LoginScenario)
    .exec(getEn)
    .exec(AddArticleScenario)


}
