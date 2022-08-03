package utils

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.catalog.CatalogTable

trait TableDB {

  def getTableMetadata(
      spark: SparkSession,
      nameDB: String,
      nameTable: String
  ): CatalogTable = {
    val tableID =
      spark.sessionState.sqlParser.parseTableIdentifier(s"$nameDB.$nameTable")
    val tableMetadata: CatalogTable =
      spark.sessionState.catalog.getTempViewOrPermanentTableMetadata(tableID)
    tableMetadata
  }

  def alterTableActual(
      spark: SparkSession,
      nameDB: String,
      nameTable: String,
      nameTableNew: String
  ): Unit = {
    import spark.implicits._
    val tablesDB = spark.sqlContext.tables(nameDB)
    // TODO: return value
    val res =
      if (
        tablesDB
          .filter('tableName.equalTo(nameTableNew))
          .rdd
          .isEmpty() == false
      ) {
        if (
          tablesDB
            .filter('tableName.equalTo(nameTable))
            .rdd
            .isEmpty() == false
        ) spark.sql(s"DROP TABLE IF EXISTS $nameDB.$nameTable")
      }
    spark.sql(s"ALTER TABLE $nameDB.$nameTableNew RENAME TO $nameDB.$nameTable")
  }

}
