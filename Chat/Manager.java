package com.Chat;

import java.util.Vector;

/**
 * Manager to manage all connections<br />
 * Singleton Pattern
 * @author Gray
 */
public class Manager {

	private static final Manager M = new Manager();
	private Vector<Chat> csVector = new Vector<Chat>();
	
	/**
	 * Private constructor to march Singleton Pattern.
	 */
	private Manager() {}
	
	/**
	 * Get ChatManager outside.
	 * @return M Manager to outside
	 */
	public static Manager getChatManager() {
		return M;
	}
	
	/**
	 * Add c(Chat) to csVector
	 * @param c Chat
	 */
	public void add(Chat c) {
		csVector.add(c);
	}
	
	/**
	 * Remove c(Chat) from csVector
	 * @param c Chat
	 */
	public void remove(Chat c) {
		csVector.remove(c);
	}
	
	/**
	 * Publish message to all clients
	 * @param out The output string
	 */
	public void publish(String out) {
		for (int i = 0; i < csVector.size(); i++) {
			Chat csChatSocket = csVector.get(i);
			csChatSocket.out(out);
		}
	}
}
