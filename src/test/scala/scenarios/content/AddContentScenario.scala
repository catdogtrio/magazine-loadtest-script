package scenarios.content

import configs.Utils.{dateTimeCurrentFormatted, generateNumber, getCurrentTimestamp, getRandomLetters}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import requests.MagazineApiRequests._
import requests.MagazineContentApiRequests._

object AddContentScenario {
  private val users = csv("users.csv").random

  var AddArticleScenario: ScenarioBuilder = scenario("login-scenario")
    .exec(getEnAdminContent)
    .exec(getEnNodeAdd)
    .exec(getEnNodeAddArticle)

    .exec(session => session.set("article_name", dateTimeCurrentFormatted("yyyy-MM-dd")+' '+generateNumber(8)))
    .exec(session => session.set("tag_value", "tag_"+getRandomLetters(6)))

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
    .repeat(5){
      exec(session => session.set("letter", getRandomLetters(1)))
        .exec(getEnEntityReferenceAutocompleteTaxonomyTermDefaultAtaxonomyTermFormId)
    }


    .exec(postEnNodeAddArticle3)



    .exec(session => {
      val location = session("location").as[String]
      println(s"location: $location")
      session
    })
    .exec(getEnNodeId)
//    .pause(5)

        .exec(session => {
          val changed_timestamp = session("changed_timestamp").as[String]
//          val node_id = session("node_id").as[String]
          val data_contextual_token = session("data_contextual_token").as[String]
          println(s"new_form_token: $changed_timestamp")
          println(s"data_contextual_token: $data_contextual_token")
          session
        })


    .exec(postEnContextualRender)
    .exec(postEnHistoryNodeIdRead)




    .pause(10)
}
