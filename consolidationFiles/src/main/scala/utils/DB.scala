package utils

import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.functions.col
import models.DescribeDB
import org.apache.spark.sql.catalog.Table

trait DB {

  def isDatabase(spark: SparkSession, dbName: String): Boolean = {
    spark.catalog.databaseExists(dbName)
  }

  def getLocation(spark: SparkSession, nameDB: String): String = {
    import spark.implicits._
    val sqlScript = s"DESCRIBE DATABASE $nameDB"
    val locationDB = spark
      .sql(sqlScript)
      .as[DescribeDB]
      .filter(col("database_description_item") === "Location")
      .select(col("database_description_value"))
      .as[String]
      .first()

    locationDB
  }

  def getTables(spark: SparkSession, nameDB: String): Seq[String] = {
    import spark.implicits._
    spark.catalog
      .listTables(nameDB)
      .filter(col("isTemporary").equalTo(false))
      .filter(col("tableType").equalTo("MANAGED"))
      .select(col("name"))
      .as[String]
      .collect
      .toSeq
  }

}
