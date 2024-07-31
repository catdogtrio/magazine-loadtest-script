package requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object MagazineApiRequests {

  val getEn: HttpRequestBuilder = http("GET /en")
    .get("/en")
    .check(status.is(200))

  val getEnUserLogin: HttpRequestBuilder = http("GET /en/user/login")
    .get("/en/user/login")
    .check(status.is(200))
    .check(regex("name=\"form_build_id\" value=\"(.*)\" \\/>").ofType[String].saveAs("form_build_id"))


  val getEnUserUserId: HttpRequestBuilder = http("GET /en/user/{userId}")
    .get("/en/user/1?check_logged_in=1")
    .check(status.is(200))

  val postEnUserLogin: HttpRequestBuilder = http("POST /en/user/login")
    .post("/en/user/login")
    .header("Content-Type", "application/x-www-form-urlencoded")
    .formParam("name","${login}")
    .formParam("pass","${password}")
    .formParam("form_id","user_login_form")
    .formParam("op","Log in")
    .formParam("form_build_id","${form_build_id}")
    .check(status.is(303))
    .check(bodyString.saveAs("BODY"))

}
