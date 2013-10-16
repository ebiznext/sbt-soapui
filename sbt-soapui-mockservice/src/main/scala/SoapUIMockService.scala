package com.ebiznext.sbt.plugins

import _root_.sbt._
import Keys._
import java.io.File
import sbt.classpath.ClasspathUtilities
import java.net.Socket
import java.io.OutputStream
import java.net.InetAddress

/**
 * @author stephane.manciot@ebiznext.com
 *
 */
class SoapUIMockService(val classpath : Seq[File]) {

    lazy val oldContextClassLoader = Thread.currentThread.getContextClassLoader

    lazy val classLoader = ClasspathUtilities.toLoader(classpath)

    lazy val soapUIClass = classLoader.loadClass("com.eviware.soapui.SoapUI")
    lazy val soapUICoreClass = classLoader.loadClass("com.eviware.soapui.SoapUICore")
    lazy val setSoapUICoreMethod = soapUIClass.getMethod("setSoapUICore", soapUICoreClass)
    lazy val soapUICoreExtClass = classLoader.loadClass("com.ebiznext.soapui.SoapUICoreExt")
    lazy val soapUICoreExtConstructor = soapUICoreExtClass.getConstructor()
    lazy val setStopPortMethod = soapUICoreExtClass.getMethod("setStopPort", java.lang.Integer.TYPE)

    def addThreadMonitoring(stopPort : Int) : Unit = {
      val sbtSoapUICore = soapUICoreExtConstructor.newInstance()
      setStopPortMethod.invoke(sbtSoapUICore, stopPort.asInstanceOf[AnyRef])
      setSoapUICoreMethod.invoke(null, sbtSoapUICore.asInstanceOf[AnyRef])
    }

    lazy val soapUIMockServiceClass = classLoader.loadClass("com.eviware.soapui.tools.SoapUIMockServiceRunner")

    lazy val soapUIMockServiceConstructor = soapUIMockServiceClass.getConstructor(classOf[java.lang.String])

    lazy val setProjectFileMethod = soapUIMockServiceClass.getMethod("setProjectFile", classOf[java.lang.String])
    lazy val setPortMethod = soapUIMockServiceClass.getMethod("setPort", classOf[java.lang.String])
    lazy val setBlockMethod = soapUIMockServiceClass.getMethod("setBlock", java.lang.Boolean.TYPE)
    lazy val runMethod = soapUIMockServiceClass.getMethod("run")

    def run(soapUIVersion : String, projectFile : String, port : String, noBlock : Boolean) : Unit =  {
        try{
          //Thread.currentThread.setContextClassLoader(classLoader)
          val runner = soapUIMockServiceConstructor.newInstance("SoapUI "+soapUIVersion+" SBT MockService Runner")
          setProjectFileMethod.invoke(runner, projectFile)
          setPortMethod.invoke(runner, port)
          setBlockMethod.invoke(runner, (!noBlock).asInstanceOf[AnyRef])
          runMethod.invoke(runner)
        }
        finally{
          //Thread.currentThread.setContextClassLoader(oldContextClassLoader)          
        }
    }

    def stop(stopPort : Int) : Unit = {
      try{
        val s : Socket = new Socket(InetAddress.getByName("127.0.0.1"), stopPort);
        val out : OutputStream = s.getOutputStream();
        out.write(("\r\n").getBytes());
        out.flush();
        s.close();
      }
      catch{
        case th:Throwable =>
      }
    }
}