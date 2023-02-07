ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
libraryDependencies += "org.scalamock" %% "scalamock" % "5.2.0" % Test

lazy val root = (project in file("."))
  .settings(
    name := "scala_file_processor"
  )
