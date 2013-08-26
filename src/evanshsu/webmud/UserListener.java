package evanshsu.webmud;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Evans Hsu (evanshsu@gmail.com)
 */
public class UserListener implements HttpSessionListener{

	public void sessionCreated(HttpSessionEvent event) {
		try {
			HttpSession session = event.getSession();
			UserSession.getInstance(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		try {
			HttpSession session = event.getSession();
			UserSession.destroy(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
