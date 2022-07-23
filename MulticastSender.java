import java.io.*;
import java.net.*;
import java.util.Scanner;


public class MulticastSender {
	public static void main(String[] args) {
		MulticastSocket socket;
		InetAddress group;
		String groupIP = "239.1.2.3";
		int port = 3456;
		String msg;
		int msgCount = 1;

		try {
			if (args.length != 1)
				msg = "Hello! Multicasting.";
			else
				msg = args[0];

			group = InetAddress.getByName(groupIP);

			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), group, port);
			Thread multicastReceiverThread = new Thread(new MulticastReceiver(group, port, msg));
			multicastReceiverThread.start();
			
			socket = new MulticastSocket(port);
			socket.setTimeToLive(32);
			Scanner scan = new Scanner(System.in);

			while (!msg.equals("bye")) {
				System.out.println("Message: " + msgCount);

				socket.send(packet);
				msg = "";
				msg += scan.nextLine();
				msgCount += 1;
				multicastReceiverThread.interrupt();
				packet = new DatagramPacket(msg.getBytes(), msg.length(), group, port);
			}

			System.out.println("Message: " + msgCount);
			scan.close();
			packet = new DatagramPacket(msg.getBytes(), msg.length(), group, port);
			socket.send(packet);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}