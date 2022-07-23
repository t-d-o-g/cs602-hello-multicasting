import java.io.*;
import java.net.*;


public class MulticastReceiver implements Runnable {
	static final int MAX_LENGTH = 100;
	private InetAddress group;
	private int port;
	private String msg;

	public MulticastReceiver(InetAddress group, int port, String msg) {
		this.group = group;
		this.port = port;
		this.msg = msg;
	}

	public void run() {
		try {
			MulticastSocket s = new MulticastSocket(port);
			s.joinGroup(group);

			while (true) {
				byte[] data = new byte[MAX_LENGTH];
				DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
				s.receive(packet);
				msg = new String(packet.getData()).trim();
				System.out.println("Message: " + msg);
				System.out.println("Multicast group IP: " + group);
				System.out.println("Mutlicast group port: " + port);
				System.out.println("Message length: " + msg.length());
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}