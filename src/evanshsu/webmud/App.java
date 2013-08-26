package evanshsu.webmud;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Evans Hsu (evanshsu@gmail.com)
 */
public class App implements ServletContextListener {

	private static String URI = null;
	
	public static final String DOT = ".";
	public static final String URL = "url";
	public static final String PORT = "port";
	public static final String ENCODING = "encoding";
	public static final String TRIGGER_USERNAME = "trigger_username";
	public static final String TRIGGER_PASSWORD = "trigger_password";
	
	public void contextDestroyed(ServletContextEvent event) {
	}

	public void contextInitialized(ServletContextEvent event) {
		URI = event.getServletContext().getContextPath().replaceAll("/", "");
	}
	
	public static String getUrl() {
		return PropUtils.get(URI + DOT + URL);
	}
	
	public static String getPort() {
		return PropUtils.get(URI + DOT + PORT);
	}
	
	public static String getEncoding() {
		return PropUtils.get(URI + DOT + ENCODING);
	}
	
	public static String getTriggerUsername() {
		return PropUtils.get(URI + DOT + TRIGGER_USERNAME);
	}
	
	public static String getTriggerPassword() {
		return PropUtils.get(URI + DOT + TRIGGER_PASSWORD);
	}
	
	
}
