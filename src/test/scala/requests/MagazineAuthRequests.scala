package requests

import configs.Parameters.defaultPassword
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

/** Represents login requests */
object MagazineAuthRequests {

  val getEn: HttpRequestBuilder = http("GET /en")
    .get("/en")
    .check(status.is(200))

  val getEnUserLogin: HttpRequestBuilder = http("GET /en/user/login")
    .get("/en/user/login")
    .check(status.is(200))
    .check(regex("name=\"form_build_id\" value=\"(.*)\" \\/>").ofType[String].saveAs("form_build_id"))

  val postEnUserLogin: HttpRequestBuilder = http("POST /en/user/login")
    .post("/en/user/login")
    .header("Content-Type", "application/x-www-form-urlencoded")
    .formParam("name", "${login}")
    .formParam("pass", defaultPassword)
    .formParam("form_id", "user_login_form")
    .formParam("op", "Log in")
    .formParam("form_build_id", "${form_build_id}")
    .check(status.is(303))
    .check(header("Location").saveAs("location"))

  val getEnUserUserId: HttpRequestBuilder = http("GET /en/user/{userId}")
    .get("${location}")
    .check(status.is(200).saveAs("login_status_code"))
    .check(regex("\\/en\\/user\\/logout\\?token=([^\\\"]+)").ofType[String].saveAs("logout_token"))

  val getEnUserLogout: HttpRequestBuilder = http("GET /en/user/logout")
    .get("/en/user/logout?token=${logout_token}")
    .check(status.is(302))

}
