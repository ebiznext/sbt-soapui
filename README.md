sbt-soapui
==========

an sbt plugin to automaticaly launch mock services defined within a soapui project

## Requirements

* [SBT 0.13+](http://www.scala-sbt.org/)


## Quick start

Add plugin to *project/plugins.sbt*:

```scala

resolvers += "Sonatype Repository" at "https://oss.sonatype.org/content/groups/public"

addSbtPlugin("com.ebiznext.sbt.plugins" % "sbt-soapui-mockservice" % "0.1.1")
```

For *.sbt* build definitions, inject the plugin settings in *build.sbt*:

```scala
seq(soapui.settings :_*)
```

For *.scala* build definitions, inject the plugin settings in *Build.scala*:

```scala
Project(..., settings = Project.defaultSettings ++ com.ebiznext.sbt.plugins.SoapUIMockServicePlugin.soapui.settings)
```

## Configuration

Plugin keys are located in `com.ebiznext.sbt.plugins.SoapUIMockServicePlugin.Keys`

### Add SoapUI projects

```scala
soapui.mockServices := Seq(
  soapui.MockService( (resourceDirectory in Test).value / "soapui-project.xml", "9001"),
  ...
) 
```
