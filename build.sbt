name := "orToolDemo"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  // scalatest is exlcuded due to conflict with testing libraries.
  "org.apache.spark" %% "spark-core" % "2.1.0" % "provided"
    exclude ("org.scalatest", "scalatest_2.11"),
  "org.apache.spark" %% "spark-sql" % "2.1.0" % "provided",
  "org.apache.spark" % "spark-hive_2.11" % "2.1.0" % "provided",
  "org.apache.spark" % "spark-mllib_2.11" % "2.1.0" % "provided",
  // Command line parser
  "com.frugalmechanic" %% "scala-optparse" % "1.1.1",
  //  SBT Junit is supported through junit-interface
  "com.novocode" % "junit-interface" % "0.11" % "test",
  // scalacheck added explicitely to avoid below error due to higher version of scalacheck.
  // "org.scalacheck" % "scalacheck_2.11" % "1.12.5" % "test",
  //  ScalaTest is test framework for scala
  // "org.scalatest" % "scalatest_2.11" % "3.0.1" % "test",
  //  Added as this is optional dependency for generating HTML reports in scalatest.
  "org.pegdown" % "pegdown" % "1.6.0" % "test",
  //  spark test framework.
  "com.holdenkarau" % "spark-testing-base_2.11" % "2.1.0_0.6.0" % "test",
  // Google OR tool in nexus
  "com.google" % "ortools" % "CentOS-7.3.1611-64bit_v6.4.4495"
)