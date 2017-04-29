package com.Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Keep listening connection looply.
 * @author Gray
 */
public class ServerListener extends Thread {
	
	/**
	 * Keep listening connection looply.
	 */
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(12345);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Client connect 12345 port");
				Chat c = new Chat(socket);
				Manager.getChatManager().add(c);
				c.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
