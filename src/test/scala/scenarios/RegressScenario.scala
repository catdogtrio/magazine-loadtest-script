package scenarios

import utils.Parameters.{sqlTags, sqlUsers, userDb}
import io.gatling.core.Predef._
import io.gatling.core.feeder.FeederBuilderBase
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.jdbc.Predef.jdbcFeeder
import requests.MagazineContentRequests._
import scenarios.content.AddContentScenario._
import scenarios.login.LoginScenario._
import scenarios.login.LogoutScenario._

import scala.concurrent.duration.{DurationInt, FiniteDuration}

/** Represents a regress scenario of loadtest */
object RegressScenario {
  private val users: FeederBuilderBase[Any]#F = jdbcFeeder(userDb._1, userDb._2, userDb._3, sqlUsers).random
  private val tags: FeederBuilderBase[Any]#F = jdbcFeeder(userDb._1, userDb._2, userDb._3, sqlTags).random

  def regressScenario (pacing1: Int, pacing2: Int, duration: FiniteDuration): ScenarioBuilder = scenario("RegressScenario").during(duration){
    pace(pacing1.seconds, pacing2.seconds)
      .feed(users)
      .feed(tags)
      .exec(LoginScenario)
      .doIf(session => session("login_status_code").as[Integer] == 200) {
        exec(getEn)
          .exec(AddArticleScenario)
          .exec(LogoutScenario)
      }
  }


}
