package com.Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerListener extends Thread {

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			while (true) {
				Socket socket = serverSocket.accept();
				JOptionPane.showMessageDialog(null, "COM connect us with 12345 port");
				ChatSocket cs = new ChatSocket(socket);
				ChatManager.getChatManager().add(cs);
				cs.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}