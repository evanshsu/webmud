package evanshsu.webmud;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evans Hsu (evanshsu@gmail.com)
 */
public class View {

	private List<String> mudMsgs = new ArrayList<String>();
	private List<String> userActions = new ArrayList<String>();
	private List<Integer> userActionsOrder = new ArrayList<Integer>();
	private StringBuffer buffer = new StringBuffer();
	
	public void add(char ch, boolean isCooked) {
		if(isCooked) {
			buffer.append(ch);
		}
		else {
			if(ch == '<')
				buffer.append("&lt");
			else
				buffer.append(ch);
		}
		
		if(ch == 10) {
			mudMsgs.add(buffer.toString());
			buffer = new StringBuffer();
		}
	}

	public void addUserAction(String action) {
		userActionsOrder.add(mudMsgs.size() + 1);
		userActions.add(action);
	}
	
	public List<String> pop() {
		List<String> raws = popRaw();
		List<String> ret = new ArrayList<String>();
		for(String raw: raws) {
			ret.add(raw.replaceAll("\r", "\n").replaceAll("\n\n", "\n").replaceAll("\n", "<br>").replaceAll(" ", "&nbsp;").replaceAll("XXXSPACEXXX"," "));
		}
		
		return ret;
	}
	
	private List<String> popRaw() {
		StringBuffer buffer = null;
		List<String> mudMsgs = null;
		List<String> userActions = null;
		List<Integer> userActionsOrder = null;
		
		synchronized(this.buffer) {
			// quickly swap to reduce thread lock time
			buffer = this.buffer; 
			this.buffer = new StringBuffer();
			
			mudMsgs = this.mudMsgs;
			this.mudMsgs = new ArrayList<String>();
			
			userActions = this.userActions;
			this.userActions = new ArrayList<String>();
			
			userActionsOrder = this.userActionsOrder;
			this.userActionsOrder = new ArrayList<Integer>();
		
		}
		
		List<String> ret = new ArrayList<String>();
		
		mudMsgs.add(buffer.toString());
		
		for(int i=0; i < mudMsgs.size(); i++) {
			ret.add(mudMsgs.get(i));
			for(int j=0; j < userActionsOrder.size(); j++) {
				if(i == userActionsOrder.get(j)) {
					ret.add(userActions.get(j));	
				}
			}
		}
				
		return ret;
	}
	
	
	
}
