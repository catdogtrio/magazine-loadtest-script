package scenarios.content

import configs.Parameters.{sqlTags, userDb}
import configs.Utils.{dateTimeCurrentFormatted, generateNumber, getCurrentTimestamp, getRandomLetters}
import io.gatling.core.Predef._
import io.gatling.core.feeder.FeederBuilderBase
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.jdbc.Predef.jdbcFeeder
import requests.MagazineContentApiRequests._

object AddContentScenario {

  private val tags: FeederBuilderBase[Any]#F = jdbcFeeder(userDb._1, userDb._2, userDb._3, sqlTags).random

  var AddArticleScenario: ScenarioBuilder = scenario("login-scenario")

    .exitBlockOnFail(
      group("Add new article to magazine"){
        feed(tags)
          .exec(getEnAdminContent)
          .exec(getEnNodeAdd)
          .exec(getEnNodeAddArticle)

          .exec(session => session.set("article_name", dateTimeCurrentFormatted("yyyy-MM-dd") + ' ' + generateNumber(8)))

          .exec(session => session.set("timestamp", getCurrentTimestamp))
          .exec(session => session.set("date", dateTimeCurrentFormatted("yyyy-MM-dd")))
          .exec(session => session.set("time", dateTimeCurrentFormatted("HH:mm:ss")))

          .exec(postEnNodeAddArticle1)
          .exec(postEnMediaLibrary)

          .exec(session => session.set("timestamp", getCurrentTimestamp))
          .exec(session => session.set("date", dateTimeCurrentFormatted("yyyy-MM-dd")))
          .exec(session => session.set("time", dateTimeCurrentFormatted("HH:mm:ss")))
          .exec(postEnNodeAddArticle2)


          // tag searching
          .repeat(4) {
            exec(session => session.set("letter", getRandomLetters(1)))
              .exec(getEnEntityReferenceAutocompleteTaxonomyTermDefaultAtaxonomyTermFormId)
          }
          // %10 - create new tag, 90% - use tag from selected
          .randomSwitch(
            10.0 -> exec(session => session.set("tag_value", "tag_" + getRandomLetters(6))),
            90.0 -> exec(session => session.set("tag_value", "${tag}"))
          )
          .exec(getEnEntityReferenceAutocompleteTaxonomyTermDefaultAtaxonomyTermFormId)


          .exec(postEnNodeAddArticle3)
          .exec(getEnNodeId)
          .exec(postEnContextualRender)
          .exec(postEnHistoryNodeIdRead)
      }
    )

}
