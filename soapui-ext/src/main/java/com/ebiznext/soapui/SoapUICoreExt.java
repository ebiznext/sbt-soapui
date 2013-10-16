/**
 * 
 */
package com.ebiznext.soapui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.eviware.soapui.DefaultSoapUICore;
import com.eviware.soapui.model.mock.MockRunner;
import com.eviware.soapui.model.settings.Settings;
import com.eviware.soapui.monitor.MockEngine;
import com.eviware.soapui.settings.HttpSettings;

/**
 * @author stephane.manciot@ebiznext.com
 * 
 */
public class SoapUICoreExt extends DefaultSoapUICore {

	private int stopPort = 8081;

	public SoapUICoreExt() {
		super(null, DEFAULT_SETTINGS_FILE);
		getSettings().setBoolean(HttpSettings.LEAVE_MOCKENGINE, true);
	}

	public void setStopPort(int stopPort){
		this.stopPort = stopPort;
	}

	/* (non-Javadoc)
	 * @see com.eviware.soapui.DefaultSoapUICore#buildMockEngine()
	 */
	@Override
	protected MockEngine buildMockEngine() {
		MockEngine engine = super.buildMockEngine();
		Thread monitor = new MonitorThread(getSettings(), engine, stopPort);
        monitor.start();
		return engine;
	}

	private static class MonitorThread extends Thread {

		private ServerSocket socket;

		private MockEngine instance;

		private Settings settings;

		private final static Logger log = Logger.getLogger( MonitorThread.class );

		public MonitorThread(Settings settings, MockEngine instance, int port) {
			this.instance = instance;
			this.settings = settings;
			setDaemon(true);
			setName("StopMonitor");
			try {
				socket = new ServerSocket(port, 1,
						InetAddress.getByName("127.0.0.1"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void run() {
			log.info("*** running jetty 'stop' thread");
			Socket accept;
			try {
				accept = socket.accept();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(accept.getInputStream()));
				reader.readLine();
				log.info("*** stopping jetty embedded server");
				settings.setBoolean(HttpSettings.LEAVE_MOCKENGINE, false);
				for(MockRunner mockRunner : instance.getMockRunners()){
					log.info("Stopping SoapUI SBT MockService Runner started on port => "
							+ mockRunner.getMockService().getPort());
					instance.stopMockService(mockRunner);
				}
				settings.setBoolean(HttpSettings.LEAVE_MOCKENGINE, true);
				accept.close();
				socket.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
