package com.Client;

/**
 * MessageUpdater.<br />
 * Update output message(Receive message)
 * @author Gray
 * @MemberVar name Cilent's name<br />
 * 			  addr IP address<br />
 * 			  sBuilder StringBuilder get from server<br />
 */
public class MessageUpdater extends Thread {
	private Net net;
	private String name;
	private String addr;
	private StringBuilder sBuilder;
	private Client client;
	
	
    /**
     * Constructors. Set name, addr, client.
     */	
	public MessageUpdater(String name, String addr, Client client) {
		this.name = name;
		this.addr = addr;
		this.client = client;
	}

	/**
	 * Muti thread to get message from server every 100 longmills
	 */
	@Override
	public void run() {
		net = new Net(name,addr);
		while (true) {
			sBuilder = net.getMessage();
			client.updateMessage(sBuilder.toString());
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
