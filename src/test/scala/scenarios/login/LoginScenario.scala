package scenarios.login

import utils.Parameters.{sqlUsers, userDb}
import io.gatling.core.Predef._
import io.gatling.core.feeder.FeederBuilderBase
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.jdbc.Predef.jdbcFeeder
import requests.MagazineUserRequests._

object LoginScenario {

  var LoginScenario: ScenarioBuilder = scenario("LoginScenario")
    .group("Login into magazine as author"){
      exec(getEnUserLogin)
        .exec(postEnUserLogin)
        .exec(getEnUserUserId)
    }
}
