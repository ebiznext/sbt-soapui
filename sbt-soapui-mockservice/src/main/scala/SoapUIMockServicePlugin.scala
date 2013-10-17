package com.ebiznext.sbt.plugins

import sbt._
import sbt.Keys._
import java.io.File

/**
 * @author stephane.manciot@ebiznext.com
 *
 */
object SoapUIMockServicePlugin extends Plugin {

  trait Keys {
    lazy val Config = config("soapui") hide
    lazy val soapuiVersion = settingKey[String]("soapui version")
    lazy val soapuiStopPort = settingKey[Int]("soapui stop port")
    lazy val mock = taskKey[Unit]("runs soapui mockServices")
    lazy val mockServices = settingKey[Seq[soapui.MockService]]("mock services to run")
    
  }

  private object SoapUIDefaults extends Keys {
    
    val settings = Seq(
        soapuiVersion := "4.5.0",
        soapuiStopPort := 8081,
        // ajout des dépendances soapui dans la configuration spécifique au plugin
        libraryDependencies ++= Dependencies.dependencies(soapuiVersion.value)(Seq(Config)),
        mockServices := Nil)
  }

  // to avoid namespace clashes, use a nested object
  object soapui extends Keys {


    case class MockService(
      projectFile : File, // The soapUI project file to test with
      // settingsFile : File // Specifies soapUI settings file to use
      //path : String, //The path to listen on
      port : String = "9999" //The port to listen on
      //globalProperties : Seq[String], 
      //mockService : String, // The mockservice to run
      //noBlock : Boolean = true, 
      //projectProperties : Seq[String] //Specified project property values
      ) 
    {
    }

    val settings = Seq(ivyConfigurations += Config)  ++ SoapUIDefaults.settings ++ Seq(
        // définition de la tâche mock
        mock := {
          val s: TaskStreams = streams.value
          val classpath : Seq[File] = update.value.select( configurationFilter(name = "soapui") )
          val service = new SoapUIMockService(classpath)
          s.log.info("Stopping Previously started SoapUI SBT MockService Runners")
          service.stop(soapuiStopPort.value)
          service.addThreadMonitoring(soapuiStopPort.value)
          mockServices.value.foreach {mockService =>
                val projectFile : String = mockService.projectFile.getAbsolutePath
                service.run(soapuiVersion.value, projectFile, mockService.port, true)
          }
        },
        //test in IntegrationTest <<= (test in IntegrationTest) dependsOn (mock in IntegrationTest),
        test in Test <<= (test in Test) dependsOn (mock in Test)
    )
  }
}
