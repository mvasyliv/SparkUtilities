package utils

import org.apache.spark.sql.SparkSession

trait Partitions {

  /** Get partitionBy columns table
    * @param spark
    *   \- SparkSession
    * @param db
    *   \- Database name
    * @param tbl
    *   \- Table name
    * @return
    *   \- Array columns name Option[Array[String]] or None
    */
  def get(
      spark: SparkSession,
      db: String,
      tbl: String
  ): Option[Array[String]] = {
    import spark.implicits._

    val tableName = s"$db.$tbl"
    val describeTable = spark
      .sql(s"DESCRIBE $tableName")
      .filter('col_name.like("%# Partition Information%"))

    if (describeTable.count() > 0) {
      val partitionCols = spark
        .sql(s"SHOW PARTITIONS $tableName")
        .as[String]
        .first
        .split('/')
        .map(_.split("=").head)
      Some(partitionCols)
    } else None

  }

}
