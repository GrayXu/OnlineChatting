package com.Chat;

import java.util.Vector;

public class Manager {

	private static final Manager M = new Manager();
	private Vector<Chat> csVector = new Vector<Chat>();

	private Manager() {}
	
	public static Manager getChatManager() {
		return M;
	}


	public void add(Chat c) {
		csVector.add(c);
	}

	public void remove(Chat c) {
		csVector.remove(c);
	}

	public int getUserID(Chat c) {
		return csVector.indexOf(c);
	}

	public void publish(Chat c, String out) {
		for (int i = 0; i < csVector.size(); i++) {
			Chat csChatSocket = csVector.get(i);
			if (!c.equals(csChatSocket)) {
				csChatSocket.out("User " + csVector.indexOf(c) + " : " + out);
			}
		}
	}
}
