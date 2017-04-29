package com.Client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * OnlineChating Client GUI
 * @author Gray
 * @MemberVar  		dialog 	   ->Dialog to input username and IP address<br />
 * 					field  	   ->TextField in Dialog<br />
 * 					txtrOut	   ->Output TextArea(muti lines)<br />
 * 					txtrOIn	   ->Input TextArea(muti lines)<br />
 * 					Others	   ->Literally
 */
public class Client extends JFrame {
	private static final long serialVersionUID = 1L;
	private String name;
	private int screenWidth;
    private int screenHeight;
    private Dialog dialog;
    private JTextField field;
    private Net net;
    private String IPaddr;
    private JTextArea txtrOut;
    private JTextArea txtrIn;
    private MessageUpdater mu;
    
    //Use regex to confirm the format of IP is correct.
    private String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    
    /**
     * Constructors. Get dimension to make setting component center convient.
     */
	public Client() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//Get dimension
    	screenWidth = dim.width;
    	screenHeight = dim.height;
	}
	
	/**
	 * Public Output textarea to MessageUpdater class.
	 * @param str A string to output
	 */
	public void updateMessage(String str){
		txtrOut.append("\n" + str);
	}
	
	/**
	 * Center component
	 * @param c the component waiting to be centered
	 */
	private void setCenter(Component c){
		((Window) c).pack();
		c.setLocation((screenWidth-c.getWidth())/2,(screenHeight-c.getHeight())/2);
		c.setVisible(true);	
	}
	
	/**
	 * Dialog to input user name and IP address<br />
	 * Only inputting correct IP addr can enter main window. 
	 */
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
	//  Binding dialog to input and set username.
		dialog = new Dialog(this, true);
		dialog.setTitle("Input");
		field = new JTextField(10);
		JButton buttonName = new JButton("User Name");
		buttonName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				name = field.getText();
				if (name.equals("")) {
					name = "Anonymous";//default name -> Anonymous
				}
				dialog.dispose();
			}
		});
		dialog.add(field, BorderLayout.NORTH);
		dialog.add(buttonName, BorderLayout.SOUTH);
		this.setCenter(dialog);
		
	//	Input IP adddress
		dialog = new Dialog(this, true);
		dialog.setTitle("Input");
		field = new JTextField(15);
		buttonName = new JButton("IP address of Service");
		buttonName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isType = false;
				IPaddr = field.getText();
				if (IPaddr.matches(regex)) {
					isType = true;
				}
				field.setText("IP²»ºÏ·¨");
				if (isType) {
					dialog.dispose();
					mainPattern();//Only inputting correct IP addr can enter main window. 
				}
			}
		});
		dialog.add(field, BorderLayout.NORTH);
		dialog.add(buttonName, BorderLayout.SOUTH);
		this.setCenter(dialog);
	}
	
	/**
	 * Create the frame.
	 */
	public void mainPattern(){
		
		net = new Net(name,IPaddr);
	//Publish textarea 	
		txtrOut = new JTextArea();
		txtrIn = new JTextArea();
		txtrOut.setEditable(false);
		txtrOut.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));//set fonts
		txtrOut.setText("Welcome to this CHAT, "+name);
		txtrOut.setBounds(0, 15, 428, 148);
		getContentPane().add(txtrOut);
	//Send message to service
		JButton btnNewButton = new JButton("Sent");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!net.sendMessage(txtrIn.getText())){//if it can't connect with server
					txtrOut.append("\nSorry, check your network right or not, or contact with Manager of this Server");
				}
				txtrIn.setText("");
			}
		});
		btnNewButton.setBounds(320, 200, 108, 29);
		getContentPane().add(btnNewButton);
	//Textarea to send message 
		txtrIn.setFont(new Font("Microsoft JhengHei UI Light", Font.PLAIN, 13));
		txtrIn.setText("Type in your message!");
		txtrIn.setBounds(0, 189, 305, 55);
		getContentPane().add(txtrIn);
	//sent center and pack
		this.setLocation((screenWidth-this.getWidth())/2,(screenHeight-this.getHeight())/2);
		this.setVisible(true);	
		setResizable(false);
		this.setTitle("OnlineChatting--Powered By Gray");
		mu = new MessageUpdater(name, IPaddr, this);
		mu.start();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Client().init();
	}
}
