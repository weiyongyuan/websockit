import sbt._
import sbt.Keys._
import com.typesafe.sbt.SbtScalariform.scalariformSettings

import scalariform.formatter.preferences._

import com.typesafe.sbt.SbtScalariform._

import com.typesafe.sbt.SbtStartScript._


object WebsockitBuild extends Build {

  lazy val kit = Project(
    id = "kit",
    base = file("."),
    settings = preSettings ++ Seq(
      name := "websockit",
      organization := "me.zhongl",
      version := "0.1",
      scalaVersion := "2.10.2",
      scalacOptions ++= Seq("-unchecked", "-deprecation", "-optimize"),
      javacOptions ++= Seq("-target", "1.6", "-source", "1.6"),
      libraryDependencies ++= compileDependencies ++ testDependencies,
      ScalariformKeys.preferences := FormattingPreferences()
        .setPreference(DoubleIndentClassDeclaration, true)
        .setPreference(PreserveDanglingCloseParenthesis, true)
        .setPreference(AlignSingleLineCaseStatements, true)
        .setPreference(AlignParameters, true)
    )
  )

  lazy val preSettings = Project.defaultSettings ++
                         scalariformSettings ++
                         startScriptForClassesSettings

  lazy val compileDependencies = Seq(
    "io.netty" % "netty-codec-http" % "4.0.3.Final",
    "com.jayway.jsonpath" % "json-path" % "0.8.1",
    "ch.qos.logback" % "logback-classic" % "1.0.13",
    "com.twitter" %% "util-eval" % "6.3.7"
  )

  lazy val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % "1.9.1"
  ) map { _ % "test" }
}
