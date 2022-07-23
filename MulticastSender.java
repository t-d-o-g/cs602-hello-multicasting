import java.io.*;
import java.net.*;


public class MulticastSender {
	public static void main(String[] args) {
		MulticastSocket s;
		InetAddress group;
		String groupIP = "239.1.2.3";
		int port = 3456;
		String msg;

		try {
			if (args.length != 1)
				msg = "Hello! Multicasting.";
			else
				msg = args[0];
				
			group = InetAddress.getByName(groupIP);

			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), group, port);
			Thread recv = new Thread(new MulticastReceiver(group, port, msg));
			recv.start();
			
			s = new MulticastSocket(port);
			s.setTimeToLive(32);
			s.send(packet);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}