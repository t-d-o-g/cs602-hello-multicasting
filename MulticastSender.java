import java.io.*;
import java.net.*;


public class MulticastSender {
	public static void main(String[] args) {
		MulticastSocket s;
		InetAddress group;
		String msg;

		try {
			if (args.length != 1)
				msg = "Hello! Multicasting.";
			else
				msg = args[0];
			group = InetAddress.getByName("239.1.2.3");
			s = new MulticastSocket(3456);
			s.setTimeToLive(32);

			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), group, 3456);
			s.send(packet);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}