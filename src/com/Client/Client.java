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
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * OnlineChatting Client GUI
 * @author Gray
 * @MemberVar  		dialog 	   ->Dialog to input username and IP address<br />
 * 					field  	   ->TextField in Dialog<br />
 * 					textAreaGet	   ->MessageGet TextArea(muti lines)<br />
 * 					textAreaOut	   ->MessageOutput TextArea(muti lines)<br />
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
    private JTextArea textAreaGet;
    private JTextArea textAreaOut;
//    private MessageUpdater mu;
    
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
		textAreaGet.append("\n" + str);
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
				field.setText("IP is not in correct format.");
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
		net = new Net(name,IPaddr,this);
    	JScrollPane scrollPaneGet = new JScrollPane();
    	JScrollPane scrollPaneOut = new JScrollPane();
    	
    	//Send message to service
    	JButton btnNewButton = new JButton("Send");
    	btnNewButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			String strOut = textAreaOut.getText();
    			if(strOut.equals("")){
    				JOptionPane.showMessageDialog(null, "输入不能为空");
    			}else{
    				net.sendMessage(strOut);//In the meantime, net's stream initialize.
    				textAreaOut.setText("");
    			}
    		}
    	});
    	
    	//Auto layout setting.
    	GroupLayout groupLayout = new GroupLayout(getContentPane());
    	groupLayout.setHorizontalGroup(
    		groupLayout.createParallelGroup(Alignment.TRAILING)
    			.addGroup(groupLayout.createSequentialGroup()
    				.addContainerGap()
    				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    					.addComponent(scrollPaneGet, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
    					.addGroup(groupLayout.createSequentialGroup()
    						.addComponent(scrollPaneOut, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
    						.addPreferredGap(ComponentPlacement.RELATED)
    						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)))
    				.addContainerGap())
    	);
    	groupLayout.setVerticalGroup(
    		groupLayout.createParallelGroup(Alignment.TRAILING)
    			.addGroup(groupLayout.createSequentialGroup()
    				.addContainerGap()
    				.addComponent(scrollPaneGet, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
    				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    					.addGroup(groupLayout.createSequentialGroup()
    						.addGap(23)
    						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
    					.addGroup(groupLayout.createSequentialGroup()
    						.addPreferredGap(ComponentPlacement.RELATED)
    						.addComponent(scrollPaneOut, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
    				.addContainerGap())
    	);
    	
    	//Publish textarea 	
    	textAreaGet = new JTextArea();
    	textAreaGet.setText("Welcome to Gray's Chatting Room!");
    	textAreaGet.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
    	scrollPaneGet.setViewportView(textAreaGet);
    	textAreaGet.setEditable(false);
    	
    	//Start to listen new message, initialize input and output stream.
    	net.getMessage();
    	
    	//Textarea to send message 
    	textAreaOut = new JTextArea();
    	textAreaOut.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
    	scrollPaneOut.setViewportView(textAreaOut);
    	getContentPane().setLayout(groupLayout);
    	this.setTitle("OnlineChatting--Powered By Gray");
    	setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Client().init();
	}
}
