package scenarios.login

import configs.Parameters.{sqlUsers, userDb}
import io.gatling.core.Predef._
import io.gatling.core.feeder.FeederBuilderBase
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.jdbc.Predef.jdbcFeeder
import requests.MagazineAuthRequests._

object LoginScenario {
  private val users: FeederBuilderBase[Any]#F = jdbcFeeder(userDb._1, userDb._2, userDb._3, sqlUsers).random

  var LoginScenario: ScenarioBuilder = scenario("LoginScenario")
    .feed(users)
    .group("Login into magazine as author"){
      exec(getEnUserLogin)
        .exec(postEnUserLogin)
        .exec(getEnUserUserId)
    }
}
