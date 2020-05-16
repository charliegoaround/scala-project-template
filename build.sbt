import Dependencies._
import com.typesafe.sbt.packager.docker._

ThisBuild / scalaVersion := "2.12.10"
ThisBuild / version := IO.readLines(new File(".version")).head
ThisBuild / organization := "com.charliegoaround"
ThisBuild / organizationName := "Charlie Go Around"

val MainClass = "com.charliegoaround.scalaprojecttemplate.Main"

fork := true

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    name := "scala-project-template",
    Defaults.itSettings,
    libraryDependencies ++= Seq(
      scalaTest % "it,test",
      slf4j,
      logback
    )
  )

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
dockerUpdateLatest := true
dockerBaseImage := "openjdk:8u212-jre-alpine"
dockerCommands := dockerCommands.value.flatMap {
  case cmd @ Cmd("FROM", baseImage) =>
    List(cmd, ExecCmd("RUN", "apk", "add", "--no-cache", "bash"))
  case x => List(x)
}

addCommandAlias("fmt", ";scalafmt;test:scalafmt;it:scalafmt")
addCommandAlias(
  "prepush",
  ";clean;update" +
    ";fmt;scalafmtCheck;test:scalafmtCheck;it:scalafmtCheck" +
    ";scalastyle;test:scalastyle;it:scalastyle" +
    ";coverage;test;it:test;coverageReport"
)
