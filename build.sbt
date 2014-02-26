name := "akka-examples"

organization := "org.ardlema"

version := "0.0.1"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test" withSources() withJavadoc(),
  "com.typesafe.akka" % "akka-actor_2.10" % "2.2.3",
  "com.typesafe.akka" % "akka-testkit_2.10" % "2.2.3"
)

initialCommands := "import org.ardlema.akkaexamples._"

