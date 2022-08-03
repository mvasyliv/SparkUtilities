val projectName = "SparkUtilities"

addCommandAlias(
  "sanity",
  ";clean ;compile ;test ;scalafmtAll ;assembly"
)
lazy val root = (project in file("."))
  .settings(
    scalaVersion := "2.13.8",
    name := "sparkUtilities",
    organization := "com.vmv",
    version := "1.0",
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
  )
  .aggregate(consolidationFiles, backupDB)

lazy val consolidationFiles = (project in file("consolidationFiles")).settings(
  scalaVersion := "2.11.12",
  name := "sparkConsolidationFiles",
  organization := "com.vmv",
  version := "1.0",
  assembly / assemblyJarName := s"$projectName-${name.value}-assembly-${version.value}.jar",
  assembly / test := (Test / test).value,
  scalacOptions ++= Seq(
    //    "-Ypartial-unification",
    "-target:jvm-1.8"
  ),
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint"),
//  git.useGitDescribe := true,
//  assembly / assemblyMergeStrategy := {
//    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
//    case _                             => MergeStrategy.first
//  },
  resolvers ++= Seq(
    "Hortonworks repository" at "https://repo.hortonworks.com/content/repositories/releases/",
    "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/",
    "Second Typesafe repo" at "https://repo.typesafe.com/typesafe/maven-releases/",
    Resolver.sonatypeRepo("public")
  ),
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
  libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.2.3.1.0.6-1" % Provided,
  libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.3.2.3.1.0.6-1" % Provided,
  libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.2.3.1.0.6-1" % Provided,
  libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.4",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test",
  libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.15.2" % "test",
  libraryDependencies += "com.holdenkarau" %% "spark-testing-base" % "2.3.2_0.11.0" % "test"
)

lazy val backupDB = (project in file("backupDB")).settings(
  scalaVersion := "2.13.8",
  name := "backupDB",
  organization := "com.vmv",
  version := "1.0",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
)
