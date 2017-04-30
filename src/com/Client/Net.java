package com.Client;

import java.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author Gray
 * @MemberVar addr IP address
 *  		  sockClient 
 *            clientName
 */
public class Net {
	String addr;
	Socket sockClient;
	String clientName;
	BufferedReader reader;
	Client client;
	PrintWriter writer;
    /**
     * Constructors. Set socketName, addr.
     */	
	public Net(String socketName, String addr,Client client) {
		this.clientName = socketName;
		this.addr = addr;
		this.client = client;
	}
	
	/**
	 * Sent message through network.
	 * @param sentStr the string waiting to be sent
	 * @return whether sending process is successful or not
	 */
	public void sendMessage(String sentStr) {
		if (writer != null) {
			writer.write(clientName + " : " + sentStr + "\n");
			writer.flush();
		}else{
			client.updateMessage("Sorry, sending failed.");
		}
	}
	
	/**
	 * Get message from network in MUTI THREADS.<br />
	 * WARNNING: Should be runned first!!!!
	 */
	public void getMessage(){
		new Thread(){
			@Override
			public void run() {
				try {
					sockClient = new Socket(addr, 12345);
					reader = new BufferedReader(
							new InputStreamReader(
									sockClient.getInputStream(), "UTF-8"));
					writer = new PrintWriter(
							new OutputStreamWriter(
									sockClient.getOutputStream(),"UTF-8"));
					
					String line = null;
					while ((line = reader.readLine()) != null) {
						client.updateMessage(line);
					}
					writer.close();
					reader.close();
					writer = null;
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

}
