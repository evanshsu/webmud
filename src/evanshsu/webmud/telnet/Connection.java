package evanshsu.webmud.telnet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;

import evanshsu.webmud.App;
import evanshsu.webmud.Controller;

/**
 * @author Evans Hsu (evanshsu@gmail.com)
 */
public class Connection {
	private final int CHECK_INTERVAL = 3000;
	private final int RETRY_INTERVAL = 5000; 
	private TelnetClient telnetClient;
	private Controller controller;
	private InputHandler inputHandler;
	private String ip;
	private int port;
	private PrintWriter pr;

	public Connection(Controller controller, String ip, String port) {
		this.controller = controller;
		controller.setConnection(this);
		
		this.ip = ip;
		this.port = Integer.parseInt(port);
	}

	public void send(String msg) {
		if(msg == null) return;

		try {
			if(telnetClient.isConnected()) {
				if(pr == null) {
					pr = new PrintWriter(new OutputStreamWriter(telnetClient.getOutputStream(),App.getEncoding()));
				}			
				pr.println(msg);
				pr.flush();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void process(char ch, boolean isCooked) {
		if(controller != null)
			controller.process(ch, isCooked);
	}
	
	public boolean start() throws Exception {		
		return execute();
	}
	
	private boolean execute() throws Exception {
		disconnect();
		return connect();
	}
	
	private boolean connect() {
		try {
			pr = null;
			telnetClient = new TelnetClient();
			telnetClient.addOptionHandler(new TerminalTypeOptionHandler("VT100", false,false, true, false));
			telnetClient.connect(ip, port);
			
			inputHandler = new InputHandler(this);
			inputHandler.start();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void disconnect() throws Exception {
		try {
			controller.reset();
			if(inputHandler != null) {
				inputHandler.disable();
				inputHandler.setConnection(null);
				inputHandler = null;
			}
			if(telnetClient != null)
				telnetClient.disconnect();
		} catch (Exception e) {
			throw e;
		}
	}

	public InputStream getInputStream() {return telnetClient.getInputStream();}	
	public boolean isConnected() {return telnetClient.isConnected();}
	public void checkConnected() throws IllegalArgumentException, IOException, InterruptedException {
		telnetClient.sendAYT(CHECK_INTERVAL);
	}
	
	public void retry() {
		try {
			do {
				Thread.sleep(RETRY_INTERVAL);
			} while(execute() == false);
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
		
	public void destroy() {
		try {
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		inputHandler = null;
		controller = null;
		telnetClient = null;
	}

}


