package evanshsu.webmud;

import javax.servlet.http.HttpSession;

import evanshsu.webmud.model.Player;
import evanshsu.webmud.telnet.Connection;

/**
 * @author Evans Hsu (evanshsu@gmail.com)
 */
public class UserSession {
	private Controller controller;
	
	public boolean init(Player player) {
		if(player == null) return false;
		
		if(controller == null) {
			controller = new Controller();
			controller.setView(new View());
//			controller.addTrigger(new Trigger(App.getTriggerUsername(), player.getUsername(), 1));
//			controller.addTrigger(new Trigger(App.getTriggerPassword(), player.getPassword(), 1));
		
			Connection conn = new Connection(controller, App.getUrl(), App.getPort());
			try {
				conn.start();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	public static UserSession getInstance(HttpSession session) {
		UserSession user = (UserSession)session.getAttribute("user");
		if(user == null || user.getController() == null || user.getView() == null) {
			user = new UserSession();
			user.init(new Player());
			session.setAttribute("user", user);
		}
		return user;
	}
	
	public static void destroy(HttpSession session) {
		UserSession user = (UserSession)session.getAttribute("user");
		if(user != null) {
			Controller controller = user.getController();
			if(controller != null) {
				controller.getConnection().destroy();
				controller.setConnection(null);
				user.setController(null);				
			}
		}
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Controller getController() {
		return controller;
	}

	public View getView() {
		return controller.getView();
	}

}
