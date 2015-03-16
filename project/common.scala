import sbt._

object Common {
  import Keys._

  val defaultScalaVersion = "2.11.2"

  val testSettings:Seq[Setting[_]] = Seq(
    testOptions in Test += Tests.Cleanup { loader =>
      val c = loader.loadClass("unfiltered.spec.Cleanup$")
      c.getMethod("cleanup").invoke(c.getField("MODULE$").get(c))
    }
  )

  val settings: Seq[Setting[_]] = ls.Plugin.lsSettings ++ Seq(
    version := "0.11.3-SNAPSHOT",

    crossScalaVersions := Seq("2.9.3", "2.10.4", "2.11.2"),

    scalaVersion := defaultScalaVersion,

    organization := "net.databinder.dispatch",

    homepage :=
      Some(new java.net.URL("http://dispatch.databinder.net/")),

    publishMavenStyle := true,

    publishTo := {
      val nexus = "http://nexus.becompany.ch/nexus/content/repositories/"
      if (isSnapshot.value)
        Some("3rd party snapshots" at nexus + "thirdparty-snapshots")
      else
        Some("3rd party"  at nexus + "thirdparty")
    },

    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),

    publishArtifact in Test := false,

    licenses := Seq("LGPL v3" -> url("http://www.gnu.org/licenses/lgpl.txt")),

    pomExtra := (
      <scm>
        <url>git@github.com:dispatch/reboot.git</url>
        <connection>scm:git:git@github.com:dispatch/reboot.git</connection>
      </scm>
      <developers>
        <developer>
          <id>n8han</id>
          <name>Nathan Hamblen</name>
          <url>http://twitter.com/n8han</url>
        </developer>
      </developers>)
  )
}
