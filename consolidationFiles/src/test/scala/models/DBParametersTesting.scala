package models

import org.scalatest.funsuite.AnyFunSuite

class DBParametersTesting extends AnyFunSuite {
  private val dbParametersControl = DBParameters(
    Some("audience"),
    Some(Array("tables1", "t_2", "tbl_3"))
  )
  test("DB parameters json") {
    val jsonDBParameters =
      """{
        |"nameDB": "audience",
        |"tables": ["tables1", "t_2", "tbl_3"]
        |}""".stripMargin

    val dbParameters = DBParameters(jsonDBParameters).get

    assert(
      dbParametersControl.nameDB.getOrElse("") === dbParameters.nameDB
        .getOrElse("")
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
        |"tables": ["", ""]
        |}""".stripMargin

    val dbParameters = DBParameters(jsonDBParameters)
    dbParameters match {
      case Some(v) =>
        println(s"$v")
        assert(v.nameDB.getOrElse("") === "")
        assert(v.nameDB.getOrElse("").isEmpty)
        assert(v.tables.getOrElse(Array.empty[String]).isEmpty === false)

      case None => assert(None === None)
    }

  }
}
