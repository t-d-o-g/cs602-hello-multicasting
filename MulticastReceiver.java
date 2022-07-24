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
			MulticastSocket socket = new MulticastSocket(port);
			socket.joinGroup(group);
			
			while (!msg.equals("bye")) {
				byte[] data = new byte[MAX_LENGTH];
				DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
				socket.receive(packet);
				msg = new String(packet.getData()).trim();

				System.out.println("Multicast receiver " + Thread.currentThread().getName() + "\n\tMessage Received: " + msg + "\n\tGroup IP: " + group + "\n\tGroup Port: " + port + "\n\tMessage Length: " + msg.length() + "\n");
		  }
			socket.leaveGroup(group);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}