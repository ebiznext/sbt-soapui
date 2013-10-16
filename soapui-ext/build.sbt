name := "soapui-ext"

organization := "com.ebiznext.soapui"

version := "4.5.1"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
    "eviware" % "soapui" % "4.5.1" % "provided",
    "jetty" % "jetty" % "6.1.26" % "provided",
    "log4j" % "log4j" % "1.2.14" % "provided"
)
