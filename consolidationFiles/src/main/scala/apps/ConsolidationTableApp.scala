package apps
import models.DBParameters
import services.ConsolidationFilesInTable.consolidation
import utils.{LogHelper, SparkActions}
object ConsolidationTableApp extends SparkActions with LogHelper {
  def main(args: Array[String]): Unit = {

    DBParameters(args(0)) match {
      case Some(parameters) =>
        val spark = initSpark("ConsolidationFilesInTablesDatabase")
        consolidation(spark, parameters)
      case None =>
        logger.error("Please provide JSON AppParameters as the first argument!")
        logger.error("""required parameters:
            |{nameDB}
            |{tables}""".stripMargin)
        System.exit(1)
    }
  }

}
