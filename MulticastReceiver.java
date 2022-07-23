import java.io.*;
import java.net.*;


public class MulticastReceiver implements Runnable {
	static final int MAX_LENGTH = 100;
	private InetAddress group;
	private int port;
	private String msg;
	private boolean quit = false;

	public MulticastReceiver(InetAddress group, int port, String msg) {
		this.group = group;
		this.port = port;
		this.msg = msg;
	}

	public void run() {
		try {
			MulticastSocket socket = new MulticastSocket(port);
			socket.joinGroup(group);
			
			System.out.println("MulticastReceiver listening for messages. When you are done say bye.\n");

			while (!msg.equals("bye")) {
				byte[] data = new byte[MAX_LENGTH];
				DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
				socket.receive(packet);
				msg = new String(packet.getData()).trim();

				System.out.println("Thread: " + Thread.currentThread().getName() + "\n");
				System.out.println("Message: " + msg);
				System.out.println("Multicast group IP: " + group);
				System.out.println("Mutlicast group port: " + port);
				System.out.println("Message length: " + msg.length() + "\n");
		  }
			socket.leaveGroup(group);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void quit() {
		quit = true;
	}
}