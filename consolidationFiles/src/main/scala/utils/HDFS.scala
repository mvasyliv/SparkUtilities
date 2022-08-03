package utils
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession

trait HDFS {

  private val nameBlockSizeInConf = "dfs.blocksize"

  /** Get size folder
    * @param spark
    *   \- SparkSession
    * @param pathFolder
    *   \- Folder in HDFS (String)
    * @return
    *   \- size folder (Long)
    */
  def getSizeFolder(spark: SparkSession, pathFolder: String): Long = {
    val fs: FileSystem = FileSystem.get(spark.sparkContext.hadoopConfiguration)
    fs.getContentSummary(new Path(pathFolder)).getLength
  }

  /** Get dfs.blocksize
    * @param spark
    *   \- SparkSession
    * @return
    *   \- size block (Long)
    */
  def getBlockSize(spark: SparkSession): Long =
    spark.sparkContext.hadoopConfiguration.get(nameBlockSizeInConf).toLong

  /** @param spark
    *   \- Spark Session
    * @param pathFolder
    *   \- base folder
    * @return
    *   \- Array Paths
    */
  def getSubfolder(spark: SparkSession, pathFolder: String): Array[Path] = {
    val fs = org.apache.hadoop.fs.FileSystem
      .get(spark.sparkContext.hadoopConfiguration)
    fs.listStatus(new Path(s"$pathFolder"))
      .filter(_.isDirectory)
      .map(_.getPath)
  }

  /** Get max size subfolder
    * @param spark
    *   \- SparkSession
    * @param arraySubfolder
    *   \- Array[Path] - list subfolder base folder
    * @return
    */
  def getMaxSizeSubfolder(
      spark: SparkSession,
      arraySubfolder: Array[Path]
  ): Long = {
    arraySubfolder.map(p => getSizeFolder(spark, p.toString)).max
  }

}
