import java.io.*;
import java.net.*;
import java.util.Scanner;


public class MulticastSender {
	public static void main(String[] args) {
		MulticastSocket socket;
		InetAddress group;
		String groupIP = "239.1.2.3";
		int port = 3456;
		String msg = "Hello! Multicasting.";
		int receiverCount = 1;
		int msgCount = 1;

		try {
			group = InetAddress.getByName(groupIP);

			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), group, port);
			
			Scanner getReceiverCount = new Scanner(System.in);
			System.out.println("How many multicast receivers should get your messages? Enter an integer value.");

			try {
				receiverCount = Integer.parseInt(getReceiverCount.nextLine());
			} catch (Exception e) {
				System.out.println("Please enter an integer value.");
				receiverCount = Integer.parseInt(getReceiverCount.nextLine());
			}

			Thread[] threads = new Thread[receiverCount];
			 
			for (int i = 0; i < receiverCount; i++) {
				threads[i] = new Thread(new MulticastReceiver(group, port, msg));
				threads[i].start();
			}
			
			socket = new MulticastSocket(port);
			socket.setTimeToLive(32);
			Scanner getMsg = new Scanner(System.in);
			System.out.println("Enter messages to send to Multicast recievers. When you are done enter 'bye'.\n");

			while (!msg.equals("bye")) {
				System.out.println("Message: " + msgCount);

				socket.send(packet);
				msg = "";
				msg += getMsg.nextLine();
				msgCount += 1;
				
				for (int i = 0; i < receiverCount; i++) {
					threads[i].interrupt();
				}
				
				packet = new DatagramPacket(msg.getBytes(), msg.length(), group, port);

			}

			System.out.println("Message: " + msgCount);
			getReceiverCount.close();
			getMsg.close();
			packet = new DatagramPacket(msg.getBytes(), msg.length(), group, port);
			socket.send(packet);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}