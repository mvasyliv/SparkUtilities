object TypeDataSource extends Enumeration {
  type TypeDataSource = Value

  val S3: Value = Value("s3")
  val HDFS: Value = Value("hdfs")
  val DB: Value = Value("database")

}
