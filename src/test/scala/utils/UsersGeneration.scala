package utils

import utils.Parameters.userDb

import java.sql.{DriverManager, ResultSet}
import scala.io.Source
import scala.io.Source._
import scala.util.Using

/** This is users data generation
 * SQL request can be found in resource folder: sql_users_generation.sql
 * */
object UsersGeneration {

  private val con_str = userDb._1 + "?user=" + userDb._2 + "&password=" + userDb._3
  private val conn = DriverManager.getConnection(con_str)
  private val sql = readResourceFileToString("/sql_users_generation.sql")


  def generate(): Unit = {
    try {
      println("Start generation of user data into database.")
      val stm = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
      stm.execute(sql)
      println("End generation of user data into database.")
    } finally {
      conn.close()
    }
  }

  /**
   * This function reads data from the file and puts it into string
   *
   * @param resourcePath - path to file to read
   */
  def readResourceFileToString(resourcePath: String): String = {
    val resource = getClass.getResourceAsStream(resourcePath)
    if (resource == null) {
      throw new IllegalArgumentException(s"Resource not found: $resourcePath")
    }

    Using.resource(Source.fromInputStream(resource)) { source =>
      source.mkString
    }
  }


}
