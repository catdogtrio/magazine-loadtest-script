package scenarios.content

import utils.Parameters.{sqlTags, userDb}
import utils.HelperFunctions.{dateTimeCurrentFormatted, generateNumber, getCurrentTimestamp, getRandomLetters}
import io.gatling.core.Predef._
import io.gatling.core.feeder.FeederBuilderBase
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.jdbc.Predef.jdbcFeeder
import requests.MagazineContentRequests._

object AddContentScenario {


  var AddArticleScenario: ScenarioBuilder = scenario("login-scenario")

    .exitBlockOnFail(
      group("Add new article to magazine"){
        exec(getEnAdminContent)
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



          // %10 - create new tag
          .randomSwitch(
            10.0 -> exec(session => session.set("tag_value", "tag_" + getRandomLetters(6))),
          )


          .exec(postEnNodeAddArticle3)
          .exec(getEnNodeId)
          .exec(postEnContextualRender)
          .exec(postEnHistoryNodeIdRead)
      }
    )

}
