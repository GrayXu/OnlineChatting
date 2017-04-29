package com.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * This class take(Chat) responsibility of chating with one cilent, including sending message and getting message<br />
 * It is in muti thread.<br />
 * @BUG I would like to make this connection steady with one cilent, but in fact it break every input and output,
 * 		and re-connect again.
 * @author Gray
 */
public class Chat extends Thread {

	Socket socket;

	public Chat(Socket s) {
		this.socket = s;
	}
	
	/**
	 * Publish message to its client through socket(only one)<br />
	 * Thread-safe.
	 * @param out
	 */
	public void out(String out) {
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println(out);
			ps.close();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("break connection(IO in Out Method)");
			Manager.getChatManager().remove(this);
			e.printStackTrace();
		}
	}

	/**
	 * Read message from client.
	 */
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				Manager.getChatManager().publish(line);
			}
			br.close();

			System.out.println("break connection(br.close)");
			Manager.getChatManager().remove(this);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("break connection(IOException,can't get InputStream)");
			Manager.getChatManager().remove(this);
			e.printStackTrace();
		}

	}
}
