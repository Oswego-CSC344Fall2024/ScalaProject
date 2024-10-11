ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.1"

lazy val root = (project in file("."))
  .settings(
    name := "RD"
  )
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test