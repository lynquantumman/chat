/*
@author Cradle Lee
*/
package chat;
import chat.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ChatGUI{
	JFrame frame;
	JTextArea inputTA;
	JTextArea dispTA;
	JButton sendButton;
	int selfPort;
	int port;//port here is other's port
	String ip;//ip here is other's ip

	UDPLink link;

	public ChatGUI(int selfPort, String ip, int port){
		chatFrame();
		chatEvent();
		//create a new thread to realize the receive function
		try{
			link = new UDPLink(selfPort, ip, port);
		}
		catch(Exception exLink){
			throw new RuntimeException();
		}				
		Thread thrd = new Thread(new Receive());//link is null here
		thrd.start();
	}

	public void chatFrame(){
		frame = new JFrame("Chat");
		frame.setLayout(new FlowLayout()); 
		frame.setBounds(300,200,650,400);
		
		dispTA = new JTextArea(15,50);
			frame.add(dispTA);
		inputTA = new JTextArea(5,50);
			frame.add(inputTA);
		sendButton = new JButton("send");
			frame.add(sendButton); 
		

		frame.setVisible(true);
	}

	public void chatEvent(){//this func is to add WindowListener

		frame.addWindowListener(
			new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent eClosing){
					System.exit(0);
				}
			}
		);

		sendButton.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent eSend) 
				{
					try
					{
						String msg = inputTA.getText();
						link.send(msg);
						inputTA.setText("");
					}
					catch(Exception exSend){
						throw new RuntimeException();
					}
				}
			}
		);

	}

//inner class
	class Receive implements Runnable//multi-thread has the other implementation.
	{
		
		private String msg;
		@Override
		public void run() 
		{
			try
			{
				while(true){
					msg = ChatGUI.this.link.receive();
					ChatGUI.this.dispTA.append(msg);
				}
			
			}
			catch(Exception exReceive){
				throw new RuntimeException();
			}
		}
	}
	
}
 


