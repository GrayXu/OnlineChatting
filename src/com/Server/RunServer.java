package com.Server;

/**
 * For test!
 * @author Gray
 */
public class RunServer {
	
	public static void main(String[] args) {
		new ServerListener().start();
		System.out.println("Server is running...");
	}

}
