package evanshsu.webmud.model;

/**
 * @author Evans Hsu (evanshsu@gmail.com)
 */
public class Trigger {
	private String pattern;
	private String action;
	private boolean triggered = false;
	
	private int loop = -1;
	private int configuredLoop = -1;
	
	public Trigger(String pattern, String action) {
		this.pattern = pattern;
		this.action = action;
	}
	
	public Trigger(String pattern, String action, int loop) {
		this.pattern = pattern;
		this.action = action;
		this.loop = loop;
		this.configuredLoop = loop;
	}

	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	private String getAction() {
		triggered = true;
		loop--;
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getLoop() {
		return loop;
	}
	public void setLoop(int loop) {
		this.loop = loop;
	}
	public void reset() {
		loop = configuredLoop;
		triggered = false;
	}

	public String getMatchedAction(StringBuilder msg) {
		if(triggered || (loop <= 0 && configuredLoop > 0))  
			return null;
		
		if(msg != null && msg.indexOf(getPattern()) >= 0) 
			return getAction();
		
		return null;
	}

	public boolean isTriggered() {
		return triggered;
	}

	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}
	
	public boolean isLogin() {
		return (configuredLoop == 1) ? true : false;
	}
}
