package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.util.Random


/** Useful functions that are used in scenario */
object HelperFunctions {

  /**
   * Return current time data in format
   *
   * @param format date time format, example "yyyy-MM-dd"
   */
  def dateTimeCurrentFormatted(format: String): String = {
    val date: LocalDateTime = LocalDateTime.now()
    date.format(DateTimeFormatter.ofPattern(format))
  }

  /**
   * Return current timestamp in seconds
   */
  def getCurrentTimestamp: Long = {
    System.currentTimeMillis / 1000
  }

  /**
   * Return random number fixed length
   *
   * @param len desired length of random number
   */
  def generateNumber(len: Int): String = {
    var num = ""
    val r = new Random()
    for (i <- 1 to len) {
      num += r.nextInt(10)
    }
    num
  }

  /**
   * Return random string of chars fixed length
   *
   * @param len desired length of random string
   */
  def getRandomLetters(len: Int): String = {
    val ch_a = 'a'.toInt
    val ch_z = 'z'.toInt
    var str = ""

    for (i <- 1 to len) {
      str += (Math.random() * (ch_z - ch_a) + ch_a).toChar
    }
    str
  }

}
