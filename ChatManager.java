package com.Chat;

import java.util.Vector;

public class ChatManager {

	private static final ChatManager cm = new ChatManager();
	private Vector<ChatSocket> csVector = new Vector<ChatSocket>();

	private ChatManager() {}
	
	public static ChatManager getChatManager() {
		return cm;
	}


	public void add(ChatSocket cs) {
		csVector.add(cs);
	}

	public void remove(ChatSocket cs) {
		csVector.remove(cs);
	}

	public int getUserID(ChatSocket cs) {
		return csVector.indexOf(cs);
	}

	public void publish(ChatSocket cs, String out) {
		for (int i = 0; i < csVector.size(); i++) {
			ChatSocket csChatSocket = csVector.get(i);
			if (!cs.equals(csChatSocket)) {
				csChatSocket.out("User " + csVector.indexOf(cs) + " : " + out);
			}
		}
	}
}
