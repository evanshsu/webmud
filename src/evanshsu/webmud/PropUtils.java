package evanshsu.webmud;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Evans Hsu (evanshsu@gmail.com)
 */
public class PropUtils {
	private static final String PROP_LOCATION = "mud.properties";
	private static Properties properties = null;
	
	public static String get(String key) {
		if(properties == null) {
			init();
		}
		
		if(properties != null) {
			return properties.getProperty(key);
		}
		else {
			return null;
		}
	}
	
	public static void init() {
		InputStream inputStream = PropUtils.class.getClassLoader().getResourceAsStream(PROP_LOCATION);
        try {
        	if (properties == null) {
        		synchronized(PropUtils.class) {
        			if (properties == null) {
	        			properties = new Properties();
	        			properties.load(inputStream);
        			}
        		}
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
        	if(inputStream != null)
        		inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
