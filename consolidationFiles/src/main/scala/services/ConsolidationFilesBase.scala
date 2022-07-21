package services

trait ConsolidationFilesBase {
  def getTypeConsolidation(str: String): Unit = {}
  def getParametersConsolidation(
      typeConsolidation: String,
      strJson: String
  ): Unit = {}
}
