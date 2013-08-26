package evanshsu.webmud.telnet;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Evans Hsu (evanshsu@gmail.com)
 */
public class InputHandler extends Thread{
	private Connection conn;
	private boolean isEnable = true;
	
	public InputHandler(Connection conn) {
		this.conn = conn;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public void disable() {
		isEnable = false;
	}
	
	public void enable() {
		isEnable = true;
	}
	
	@Override
	public void run() {
		try {
			if(!isEnable)
				return;
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "MS950"));
			boolean isSkip = false;
			while(isEnable) {
				int ch = -1;
				StringBuffer colorBuffer = null;
				
				while (conn.isConnected() && (ch = br.read()) != -1) {
					if(ch == 27) {
						isSkip = true;
						colorBuffer = new StringBuffer();
					}
					
					if(isSkip) {
						colorBuffer.append((char)ch);
					}
					
					if(!isSkip) {
						conn.process((char)ch);
					}
					
					if(ch == 109) {
						isSkip = false;
						handleColor(colorBuffer.toString());
					}
				}

				Thread.sleep(500);
				
				try {
					conn.checkConnected();
				} catch (Exception e) {
					if(conn != null)
						conn.retry();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private boolean isInSpan = false;
	
	public void handleColor(String buf) {
		String color = "<spanXXXSPACEXXXclass='";
		
		if(buf.indexOf("[1m") > 0 || buf.indexOf("[0m") > 0 || buf.indexOf("37") > 0) {
			if(!isInSpan) return;
			isInSpan = false;
			color = "</span>";
		}
		else {
			if(isInSpan) return;
				
			isInSpan = true;
			
			if(buf.indexOf("[1;") > 0)
				color = color + "light";
			
			if(buf.indexOf("30") > 0)
				color = color + "black";
			else if(buf.indexOf("31") > 0)
				color = color + "red";
			else if(buf.indexOf("32") > 0)
				color = color + "green";
			else if(buf.indexOf("33") > 0)
				color = color + "yellow";
			else if(buf.indexOf("34") > 0)
				color = color + "blue";
			else if(buf.indexOf("35") > 0)
				color = color + "purple";
			else if(buf.indexOf("36") > 0)
				color = color + "cyan";
			else if(buf.indexOf("37") > 0)
				color = color + "white";
			
			color = color + "'>";
		}
		
		for(int i = 0; i < color.length(); i++) {
			conn.process(color.charAt(i));
		}
	}	
}
