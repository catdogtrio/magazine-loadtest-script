package scenarios

import configs.Parameters.{sqlTags, sqlUsers, userDb}
import io.gatling.core.Predef._
import io.gatling.core.feeder.FeederBuilderBase
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.jdbc.Predef.jdbcFeeder
import requests.MagazineAuthRequests._
import scenarios.content.AddContentScenario._
import scenarios.login.LoginScenario._
import scenarios.login.LogoutScenario._

/** Represents a regress scenario of loadtest */
object RegressScenario {
  private val users: FeederBuilderBase[Any]#F = jdbcFeeder(userDb._1, userDb._2, userDb._3, sqlUsers).random
  private val tags: FeederBuilderBase[Any]#F = jdbcFeeder(userDb._1, userDb._2, userDb._3, sqlTags).random

  var RegressScenario: ScenarioBuilder = scenario("RegressScenario")
    .feed(users)
    .feed(tags)
    .exec(LoginScenario)
    .doIf(session => session("login_status_code").as[Integer] == 200) {
      exec(getEn)
        .exec(AddArticleScenario)
        .exec(LogoutScenario)
    }

}
