package evanshsu.webmud;

import java.util.ArrayList;
import java.util.List;

import evanshsu.webmud.model.Trigger;

import evanshsu.webmud.telnet.Connection;

/**
 * @author Evans Hsu (evanshsu@gmail.com)
 */
public class Controller {
	private Connection connection;
	private StringBuilder bufferMsg = new StringBuilder();
	private List<Trigger> triggers = new ArrayList<Trigger>();
	private View view;
	
	public void process(char ch, boolean isCooked) {
		
		view.add(ch, isCooked);
		bufferMsg.append(ch);
		
		for(Trigger trigger:triggers) {
			send(trigger.getMatchedAction(bufferMsg), trigger.isLogin());
		}
		
		if(ch == 10) {
			for(Trigger trigger:triggers) {
				trigger.setTriggered(false);
			}
			bufferMsg = new StringBuilder();
		}
	}
	private void send(String action, boolean isLoginMsg) {
		if(action != null) {
			if(!isLoginMsg) {
				view.addUserAction(action);
			}
			connection.send(action);
		}
	}

	public void addTrigger(Trigger trigger) {
		triggers.add(trigger);
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public void setView(View view) {
		this.view = view;
	}

	public void reset() {
		for(Trigger trigger: triggers) 
			trigger.reset();
	}
	public Connection getConnection() {
		return connection;
	}
	public View getView() {
		return view;
	}
}
