package com.Client;

import java.net.Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
	
    /**
     * Constructors. Set socketName, addr.
     */	
	public Net(String socketName, String addr) {
		this.clientName = socketName;
		this.addr = addr;
	}
	
	/**
	 * Sent message through network.
	 * @param sentStr the string waiting to be sent
	 * @return whether sending process is successful or not
	 */
	public boolean sendMessage(String sentStr) {
		try {
			sockClient = new Socket(addr, 12345);
			//Packageing outputstream.
			BufferedWriter bw = new BufferedWriter(
									new OutputStreamWriter(
										sockClient.getOutputStream(),"UTF-8"));
			sentStr = clientName + " : " + sentStr + "\n";
			
			bw.write(sentStr);
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Get message from network.
	 * @return StringBuilder to write SB to output text area
	 */
	public StringBuilder getMessage(){
		try {
			sockClient = new Socket(addr, 12345);
			//Packageing inputstream.
			BufferedReader br = new BufferedReader(
									new InputStreamReader(
										sockClient.getInputStream(), "UTF-8"));
			String line = null;
			StringBuilder outStrB = new StringBuilder();
			while((line = br.readLine()) != null){
				outStrB.append(line);
			}
			br.close();
			return outStrB;
		} catch (IOException e) {
			e.printStackTrace();
			return new StringBuilder("Didn't connect successfully, please check your network.");
		}
		
	}

}
