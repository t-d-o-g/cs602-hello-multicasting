import java.io.*;
import java.net.*;


public class MulticastReceiver {
	public static void main(String[] args) {
		MulticastSocket s;
		InetAddress group;
		String groupIP = "239.1.2.3";
		int port = 3456;

		try {
			group = InetAddress.getByName(groupIP);
			s = new MulticastSocket(port);
			s.joinGroup(group);
			byte[] buf = new byte[100];
			DatagramPacket recv = new DatagramPacket(buf, buf.length);
			s.receive(recv);
			String msg = new String(buf).trim();

			System.out.println("Message: " + msg);
			System.out.println("Multicast group IP: " + groupIP);
			System.out.println("Mutlicast group port: " + port);
			System.out.println("Message length: " + msg.length());
			s.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}