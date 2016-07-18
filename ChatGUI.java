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
	JButton linkButton;
	int selfPort;
	int port;//port here is other's port
	String ip;//ip here is other's ip

	UDPLink link;

	public ChatGUI(){
		chatFrame();

		int selfPort=8888;
		int port=8889;//port here is other's port
		String ip="192.168.240.205";//ip here is other's ip

		chatEvent();
		Thread thrd = new Thread(new Receive(dispTA, link));
		thrd.start();
	}

	public void chatFrame(){
		frame = new JFrame("Chat");
		frame.setLayout(new FlowLayout()); 
		frame.setBounds(300,200,650,400);
		
		dispTA = new JTextArea(15,50);//7rows 10columns
			frame.add(dispTA);
		inputTA = new JTextArea(5,50);
			frame.add(inputTA);
		sendButton = new JButton("send");
			frame.add(sendButton); 
		linkButton = new JButton("link");

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


		linkButton.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent eLink) 
				{
					try{
						link = new UDPLink(selfPort, ip, port);
					}
					catch(Exception exLink){
						throw new RuntimeException();
					}
				}
			}
		);



	}

//inner class
	class Receive implements Runnable//multi-thread has the other implementation.
	{
		private UDPLink link;
		private JTextArea dispTA;
		private String msg;
		public Receive(JTextArea dispTA, UDPLink link){//initialization
			this.link = link;
			this.dispTA = dispTA;
		}


		@Override
		public void run() 
		{
			try
			{
				msg = link.receive();
				dispTA.append(msg);
			
			}
			catch(Exception exReceive){
				throw new RuntimeException();
			}
		}
	}
	
}
 


