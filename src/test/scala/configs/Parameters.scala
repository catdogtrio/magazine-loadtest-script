package configs


/** Static parameters */
object Parameters {
  val baseUrl = "http://localhost:8080/"
  val defaultPassword: String = "Yfuheprf20!("

  val userDb: (String, String, String, String) = (
    "jdbc:postgresql://localhost:5432/umami",
    "sushi",
    "flavour",
    "org.postgresql.Driver")

  val sqlUsers: String = "select name as login from public.users_field_data where pass='$2y$10$mxM8PMebElmcFQh2o/k4aOwR.anqPRgbySnaUpLIQWH8.smhYXjka' order by random()"
  val sqlTags: String = "select concat(name,' ','(',tid ,')') as tag_value from public.taxonomy_term_field_data where vid='tags' order by random()"

}
