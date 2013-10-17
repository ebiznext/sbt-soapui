import sbt._
import Keys._

/**
 * @author stephane.manciot@ebiznext.com
 *
 */
object BuildProjects extends Build {

  lazy val soapuiVersion = "4.5.1"

  def subProject(name:String) : Project = {
    Project(name, file(name))
    .settings(
      scalaVersion := "2.10.2"
    )
  }

  lazy val ext = (
    subProject("soapui-ext")
    .settings(
      organization := "com.ebiznext.soapui",
      version := soapuiVersion,
      libraryDependencies ++= Seq(
        "eviware" % "soapui" % soapuiVersion % "provided",
        "jetty" % "jetty" % "6.1.26" % "provided",
        "log4j" % "log4j" % "1.2.14" % "provided"
      )
    )
  )

  lazy val mockService = (
    subProject("sbt-soapui-mockservice")
    .dependsOn(ext)
    .settings(
      sbtPlugin := true,
      organization := "com.ebiznext.sbt.plugins",
      version := "0.1"
    )
  )

}