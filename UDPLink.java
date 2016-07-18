/*
@author Cradle Lee
*/
package chat;
import java.net.*;
public class UDPLink{
	int selfPort;
	int port;//port here is other's port
	String ip;//ip here is other's ip
	final int bufLen = 1024;
	DatagramSocket ds;
	DatagramPacket sendPacket;
	DatagramPacket receivePacket;
	
	public UDPLink(int selfPort, String ip, int port) throws Exception
	{//initialization
		//to realize the DatagramSocket and DatagramPacket
		this.selfPort= selfPort;
		this.ip = ip;
		this.port = port;
		ds = new DatagramSocket(selfPort);
	}

	public void send(String msg) throws Exception
	{ 
		byte[] buf = msg.getBytes();
		InetAddress address = InetAddress.getByName(ip);//***here need some revise.
		sendPacket = new DatagramPacket(buf, buf.length, address, port);
		ds.send(sendPacket);
		//DatagramSocket has not been closed

	}

	public String receive() throws Exception
	{
		byte[] buf = new byte[bufLen];
		receivePacket = new DatagramPacket(buf, buf.length);
		ds.receive(receivePacket);
		String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
		return msg;
	}
}

