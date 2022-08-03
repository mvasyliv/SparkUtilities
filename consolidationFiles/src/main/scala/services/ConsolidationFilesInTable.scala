package services

import models.DBParameters
import utils.{DB, HDFS, Partitions, TableDB}
import org.apache.spark.sql.{Column, SaveMode, SparkSession}
import org.apache.spark.sql.catalyst.catalog.CatalogTable
import org.apache.spark.sql.functions.col
import utils.DateTimeUtils.getCurrentTimeStamp

/** Consolidation files in database tables
  */
object ConsolidationFilesInTable
    extends Partitions
    with HDFS
    with DB
    with TableDB {

  /** Consolidation files in tables
    * @param spark
    * @param nameDB
    * @param nameTables
    */
  def consolidation(
      spark: SparkSession,
      dbParam: DBParameters
  ): Unit = {
    val nameDB = dbParam.nameDB.getOrElse("")
    val isDB = isDatabase(spark, nameDB)
    val resConslDB = if (isDB) {
      val sizeBlock = getBlockSize(spark)
      val tbls =
        if (dbParam.tables.isEmpty) getTables(spark, nameDB)
        else dbParam.tables.getOrElse(Array[String]()).toSeq
      val resConslTables =
        tbls.map(tbl => consolidationTable(spark, nameDB, tbl, sizeBlock))
    } else {
      // TODO
    }

  }

  /** Consolidation files in select table
    * @param spark
    *   \- Spark Session
    * @param nameDB
    *   \- name database
    * @param nameTable
    *   \- name table
    */
  def consolidationTable(
      spark: SparkSession,
      nameDB: String,
      nameTable: String,
      sizeBlock: Long
  ): Unit = {

    val tblMetadata: CatalogTable = getTableMetadata(spark, nameDB, nameTable)
    val locationTable = tblMetadata.location.toString
    if (tblMetadata.partitionColumnNames.isEmpty) {
      val sizeDataTable = getSizeFolder(spark, locationTable)
      val numberPartition = (sizeDataTable / sizeBlock + 1).toInt
      consolidationTableWithoutPartition(
        spark,
        nameDB,
        nameTable,
        s"${nameTable}_new_$getCurrentTimeStamp",
        numberPartition
      )
    } else {
      val subFolders = getSubfolder(spark, locationTable)
      val maxSizeSubfolder = getMaxSizeSubfolder(spark, subFolders)
      val numberPartition = (maxSizeSubfolder / sizeBlock + 1).toInt
      consolidationTableWithPartition(
        spark,
        nameDB,
        nameTable,
        tblMetadata.partitionColumnNames.map(col(_)),
        s"${nameTable}_new_$getCurrentTimeStamp",
        numberPartition
      )
    }

  }

  /** Consolidation table without partition
    * @param spark
    *   \- SparkSession
    * @param nameDB
    *   \- name database
    * @param nameTable
    *   \- name table
    * @param nameTableNew
    *   \- name new table
    * @param partitionCount
    *   \- number partition
    */
  def consolidationTableWithoutPartition(
      spark: SparkSession,
      nameDB: String,
      nameTable: String,
      nameTableNew: String,
      partitionCount: Int
  ): Unit = {
    val t = spark.table(s"$nameDB.$nameTable")

    val s = t
      .repartition(partitionCount)
      .write
      .format("parquet")
      .mode(SaveMode.Overwrite)
      .saveAsTable(s"$nameDB.$nameTableNew")

    alterTableActual(spark, nameDB, nameTable, nameTableNew)

  }

  /** Consolidation table with partition(s)
    * @param spark
    *   \- SparkSession
    * @param nameDB
    *   \- name database
    * @param nameTable
    *   \- name table current
    * @param partitionColName
    *   \- Seq[String] partition columns name
    * @param nameTableNew
    *   \- new table name
    */
  def consolidationTableWithPartition(
      spark: SparkSession,
      nameDB: String,
      nameTable: String,
      partitionColName: Seq[Column],
      nameTableNew: String,
      numberPartition: Int
  ): Unit = {

//    .repartitionByRange - try first - not good
    spark
      .table(s"$nameDB.$nameTable")
      .repartition(
        numberPartition,
        partitionColName: _*
      )
      .write
      .format("parquet")
      .partitionBy(partitionColName.map(_.toString()): _*)
      .mode(SaveMode.Overwrite)
      .saveAsTable(s"$nameDB.$nameTableNew")

    alterTableActual(spark, nameDB, nameTable, nameTableNew)
  }

  def consolidationTableWithPartitionStepByPartition(
      spark: SparkSession,
      nameDB: String,
      nameTable: String,
      nameTableNew: String
  ) {}

}
