package models
import play.api.libs.json.{Json, OFormat}
import scala.util.Try

case class S3Parameters(
    `aws-access-key`: Option[String],
    `aws-secret-key`: Option[String],
    `aws-endpoint`: Option[String],
    `s3-protocol`: Option[String],
    `s3-bucket`: Option[String],
    `s3-subpath`: Option[String],
    numPartitions: Option[Int] = None
)
object S3Parameters {
  implicit val jsonS3ParametersFormat: OFormat[S3Parameters] =
    Json.format[S3Parameters]
  def apply(appParam: String): Option[S3Parameters] = {
    Try(Json.parse(appParam).asOpt[S3Parameters]).getOrElse(None)
  }
}
