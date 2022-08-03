package utils

import java.text.SimpleDateFormat
import java.util.Calendar

object DateTimeUtils {
  def getCurrentTimeStamp: String = {
    val today: java.util.Date = Calendar.getInstance.getTime
    val timeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss")
    timeFormat.format(today)
  }

}
