scalaVersion := "2.13.8"
name := "sparkUtilities"
organization := "com.vmv"
version := "1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"

lazy val root = (project in file(".")).settings(
  scalaVersion := "2.13.8",
  name := "sparkUtilities",
  organization := "com.vmv",
  version := "1.0"
)

lazy val consolidationFiles = (project in file("consolidationFiles")).settings(
  scalaVersion := "2.13.8",
  name := "sparkConsolidatioFiles",
  organization := "com.vmv",
  version := "1.0"
)

lazy val backupDB = (project in file("backupDB")).settings(
  scalaVersion := "2.13.8",
  name := "backupDB",
  organization := "com.vmv",
  version := "1.0"
)
