package models

import org.scalatest.funsuite.AnyFunSuite

class HDFSParametersTesting extends AnyFunSuite {
  private val controlHDFSParameter =
    HDFSParameters(Some("/user/vmv/in"), Some("/tmp/user/vmv"), Some(10))

  test("HDFS Parameters JSON") {
    val jsonHDFSParameters =
      """{
        |"hdfsPath": "/user/vmv/in",
        |"hdfsPathTmp": "/tmp/user/vmv",
        |"numPartitions": 10
        |}""".stripMargin

    val hdfsParameters = HDFSParameters(jsonHDFSParameters).get
    assert(
      controlHDFSParameter.hdfsPath.getOrElse(None) === hdfsParameters.hdfsPath
        .getOrElse(None)
    )
    assert(
      controlHDFSParameter.hdfsPathTmp.getOrElse(
        None
      ) === hdfsParameters.hdfsPathTmp
        .getOrElse(None)
    )
    assert(
      controlHDFSParameter.numPartitions.getOrElse(
        None
      ) === hdfsParameters.numPartitions
        .getOrElse(None)
    )
  }
}
