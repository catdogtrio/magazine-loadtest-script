package configs

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.util.Random

object Utils {

  def dateTimeCurrentFormatted(format: String): String = {
    val date: LocalDateTime = LocalDateTime.now()
    date.format(DateTimeFormatter.ofPattern(format))
  }

  def getCurrentTimestamp: Long = {
    System.currentTimeMillis / 1000
  }

  def generateNumber(len: Int): String = {
    var num = ""
    val r = new Random()
    for (i <- 1 to len) {
      num += r.nextInt(10)
    }
    num
  }

}
