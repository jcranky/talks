
val http4sVersion = "0.18.3"
val logbackVersion = "1.2.3"

lazy val root = (project in file("."))
  .settings(
    organization := "com.jcranky",
    name := "scaladores38",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.5",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-client" % http4sVersion,
      "org.http4s"      %% "http4s-blaze-server" % http4sVersion,
      "org.http4s"      %% "http4s-circe"        % http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % http4sVersion,
      "io.circe"        %% "circe-derivation"    % "0.9.0-M2",
      "com.lihaoyi"     %% "utest"               % "0.6.4"        % Test,
      "ch.qos.logback"  %  "logback-classic"     % logbackVersion
    )
  )

testFrameworks += new TestFramework("utest.runner.Framework")

cancelable in Global := true
