package com.ebiznext.sbt.plugins

import _root_.sbt._

/**
 * @author stephane.manciot@ebiznext.com
 *
 */
object Dependencies {

  def dependencies(soapuiVersion: String)(configs : Seq[Configuration]) : Seq[ModuleID] = Seq[ModuleID](
    "com.ebiznext.soapui" %% "soapui-ext" % soapuiVersion,

    "eviware" % "soapui" % soapuiVersion,
    "eviware" % "soapui-xmlbeans" % soapuiVersion,

    "junit" % "junit" % "4.10",

    "commons-lang" % "commons-lang" % "2.4",
    "commons-io" % "commons-io" % "1.3.2",
    "commons-ssl" % "not-yet-commons-ssl" % "0.3.11",
    "commons-httpclient" % "commons-httpclient" % "3.1",
    "json" % "json-lib" % "2.2.2-jdk15",
    "ezmorph" % "ezmorph" % "1.0.5",
    "xom" % "xom" % "1.1",
    "swingx" % "swingx" % "soapui",
    "commons-codec" % "commons-codec" % "1.3",
    "rhino" % "js" % "1.7R2",
    "net-components" % "netcomponents" % "1.3.8a",
    //"xmlbeans" % "xbean" % "fixed-2.4.0",
    "xmlbeans" % "xbean_xpath" % "2.4.0",
    "xmlbeans" % "xmlpublic" % "2.4.0",
    //"xmlbeans" % "jsr173_1.0_api" % "xmlbeans-2.4.0",
    //"eviware"  % "soapui-xmlbeans" % "4.0.0",
    "eviware" % "policy-xmlbeans" % "1.5",
    "eviware" % "soap-xmlbeans" % "1.2",
    "eviware" % "wadl-xmlbeans" % "1.1",
    "eviware" % "j2ee-xmlbeans" % "1.4",
    "eviware" % "ext-xmlbeans" % "1.2",
    "saxon" % "saxon" % "9.1.0.8j",
    "saxon" % "saxon-dom" % "9.1.0.8j",
    "xmlunit" % "xmlunit" % "1.2",
    "xml-security" % "xmlsec" % "1.4.3",
    "opensaml" % "opensaml" % "1.1",
    "xalan" % "xalan" % "2.7.1",
    "wss4j" % "wss4j" % "1.5.8",
    //"bouncycastle" % "bcprov" % "jdk15-144",
    //"jtidy"    % "jtidy" % "r872-jdk15",
    //"javax.jms" % "jms" % "1.1",
    "hermesjms" % "hermes" % "1.14",
    "thoughtworks" % "xstream" % "1.3.1",
    //"net.sourceforge.htmlunit" % "htmlunit" % "2.7",
    "org.w3c.css" % "sac" % "1.3",
    //"net.sourceforge.htmlunit" % "htmlunit-core-js" % "2.7",
    "net.sourceforge.cssparser" % "cssparser" % "0.9.5",
    "net.sourceforge.nekohtml" % "nekohtml" % "1.9.14",

    "commons-cli" % "commons-cli" % "1.0",
    "commons-collections" % "commons-collections" % "3.2.1",
    "commons-logging" % "commons-logging" % "1.1.1",
    "commons-beanutils" % "commons-beanutils" % "1.7.0",
    "log4j" % "log4j" % "1.2.14",
    "xmlbeans" % "xbean" % "fixed-2.4.0",
    "org.apache.httpcomponents" % "httpclient" % "4.1.1",
    "xerces" % "xercesImpl" % "2.9.1",
    "wsdl4j" % "wsdl4j" % "1.6.2-fixed",
    //"fife" % "rsyntaxtextarea" % "1.3.4" %  Config.name
    "fife" % "rsyntaxtextarea" % "1.0-soapui",
    "bouncycastle" % "bcprov-jdk15" % "144",
    "jetty" % "jetty" % "6.1.26",
    "jetty" % "jetty-util" % "6.1.26",
    "jetty" % "servlet-api" % "2.5-20081211",
    "amf" % "flex-messaging-common" % "1.0",
    "amf" % "flex-messaging-core" % "1.0",
    "amf" % "flex-messaging-opt" % "1.0",
    "amf" % "flex-messaging-proxy" % "1.0",
    "amf" % "flex-messaging-remoting" % "1.0",
    "javax.activation" % "activation" % "1.1",
    "javax.mail" % "mail" % "1.4") map (_ % (configs map (_.name) mkString(",")))
}