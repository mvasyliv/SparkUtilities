package models

import play.api.libs.json.{Json, OFormat}
import scala.util.Try

case class DBParameters(
    nameDB: Option[String],
    tables: Option[Array[String]],
    numPartitions: Option[Int] = None
)

object DBParameters {

  implicit val jsonDBParametersFormat: OFormat[DBParameters] =
    Json.format[DBParameters]

  def apply(appParam: String): Option[DBParameters] = {
    Try(Json.parse(appParam).asOpt[DBParameters]).getOrElse(None)
  }

}
