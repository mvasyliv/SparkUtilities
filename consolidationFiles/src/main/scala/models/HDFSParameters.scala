package models
import play.api.libs.json.{Json, OFormat}

import scala.util.Try

case class HDFSParameters(
    hdfsPath: Option[String],
    hdfsPathTmp: Option[String],
    numPartitions: Option[Int] = None
)
object HDFSParameters {
  implicit val jsonHDFSParametersFormat: OFormat[HDFSParameters] =
    Json.format[HDFSParameters]

  def apply(appParam: String): Option[HDFSParameters] = {
    Try(Json.parse(appParam).asOpt[HDFSParameters]).getOrElse(None)
  }

}
