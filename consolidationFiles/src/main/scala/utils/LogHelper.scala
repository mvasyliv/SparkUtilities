package utils
import org.apache.log4j.Logger
trait LogHelper {
  val loggerName = this.getClass()
  lazy val logger = Logger.getLogger(loggerName)

}
