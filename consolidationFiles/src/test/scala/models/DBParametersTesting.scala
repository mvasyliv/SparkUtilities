package models

import org.scalatest.funsuite.AnyFunSuite

class DBParametersTesting extends AnyFunSuite {
  private val dbParametersControl = DBParameters(
    Some("audience"),
    Some(Array("tables1", "t_2", "tbl_3")),
    Some(10)
  )
  test("DB parameters json") {
    val jsonDBParameters =
      """{
        |"nameDB": "audience",
        |"tables": ["tables1", "t_2", "tbl_3"],
        |"numPartitions": 10
        |}""".stripMargin

    val dbParameters = DBParameters(jsonDBParameters).get

    assert(
      dbParametersControl.nameDB.getOrElse("") === dbParameters.nameDB
        .getOrElse("")
    )
    assert(
      dbParametersControl.numPartitions.getOrElse(
        0
      ) === dbParameters.numPartitions
        .getOrElse(0)
    )
    assert(
      dbParametersControl.tables.getOrElse(None) === dbParameters.tables
        .getOrElse(None)
    )
  }

  test("DB parameters json is Empty") {
    val jsonDBParameters =
      """{
        |"nameDB": "",
        |"tables": ["", ""],
        |"numPartitions": 
        |}""".stripMargin

    val dbParameters = DBParameters(jsonDBParameters)
    dbParameters match {
      case Some(v) =>
        println(s"$v")
        assert(v.nameDB.getOrElse(None) === "")
        assert(v.nameDB.getOrElse(None) === None)
        assert(v.numPartitions.getOrElse(None) === None)
        assert(v.tables.getOrElse(None) === None)

      case None => assert(None === None)
    }

  }
}
