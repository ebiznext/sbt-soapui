name := "soapui-ext"

organization := "com.ebiznext.soapui"

lazy val soapuiVersion = "4.5.0"

version := soapuiVersion

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
    "eviware" % "soapui" % soapuiVersion % "provided",
    "jetty" % "jetty" % "6.1.26" % "provided",
    "log4j" % "log4j" % "1.2.14" % "provided"
)

resolvers += "SOAPUI Repository" at "http://www.soapui.org/repository/maven2"

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/ebiznext/sbt-soapui</url>
  <licenses>
    <license>
      <name>MIT</name>
      <url>http://opensource.org/licenses/mit-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:ebiznext/sbt-soapui.git</url>
    <connection>scm:git:git@github.com:ebiznext/sbt-soapui.git</connection>
  </scm>
  <developers>
    <developer>
      <id>smanciot</id>
      <name>Stéphane Manciot</name>
      <url>http://www.linkedin.com/in/smanciot</url>
    </developer>
  </developers>)