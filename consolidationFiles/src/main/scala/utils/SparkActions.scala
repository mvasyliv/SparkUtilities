package utils

import org.apache.spark.sql.SparkSession

trait SparkActions {
  def initSpark(
      appName: String = "SparkApplication",
      dynamicAllocation: Boolean = false
  ): SparkSession = {
    SparkSession
      .builder()
      .appName(appName)
      .config("spark.dynamicAllocation.enabled", dynamicAllocation)
      .enableHiveSupport()
      .getOrCreate()
  }
}
