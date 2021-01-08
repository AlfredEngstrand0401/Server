package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import Client.Client;
import Player.Player;

public class Server {

	private static DatagramSocket socket;
	private static boolean running;
	private static List<Client> clients = new ArrayList<Client>();
	private static List<Player> players = new ArrayList<Player>();

	public static void start(int port) {
		try {
			socket = new DatagramSocket(port);
			running = true;
			listen();
			System.out.println("Server started on port: " + port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void broadcast(String message) {
		for (Client clients : clients) {
			send(message, clients.getAddress(), clients.getPort());
		}
	}

	public static void send(String message, InetAddress address, int port) {
		try {
			message += "\\e";
			byte[] data = message.getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void listen() {
		Thread listenThread = new Thread("listen thread") {
			public void run() {
				try {
					while (running) {

						byte[] data = new byte[1024];
						DatagramPacket packet = new DatagramPacket(data, data.length);
						socket.receive(packet);

						String message = new String(data);
						message = message.substring(0, message.indexOf("\\e"));

						// Manage messages.
						if (!isCommand(message, packet)) {
							broadcast(message);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		listenThread.start();
	}

	private static boolean isCommand(String message, DatagramPacket packet) {

		if (message.startsWith("\\con:")) {
			String[] parts = message.split(":");
			String name = parts[1];
			float x = Float.parseFloat(parts[2]);
			float y = Float.parseFloat(parts[3]);
			float z = Float.parseFloat(parts[4]);
			int age = Integer.parseInt(parts[5]);
			int health = Integer.parseInt(parts[6]);
			Player player = new Player(name, x, y, z, age, health);
			clients.add(new Client(name, packet.getAddress(), packet.getPort()));
			players.add(player);
			sendNewData(player);
			sendPlayerData();
			broadcast(name + " connected!");
			return true;
		}

		if (message.startsWith("\\move:")) {
			broadcast(message);
			return true;
		}

		if (message.startsWith("\\dis:")) {
			String name = message.substring(message.indexOf(":") + 1);
			System.out.println(name + " has disconnected.");
			broadcast(name + " has disconnected.");
			socket.close();
			return true;
		}

		if (message.startsWith("\\move:")) {
			broadcast(message);
			return true;
		}

		if (message.startsWith("\\rot:")) {
			broadcast(message);
			return true;
		}

		if (message.startsWith("\\health:")) {

			return true;
		}

		return false;

	}

	public static void stop() {
		running = false;
	}

	public static void sendPlayerData() {

		for (int i = 0; i < players.size(); i++) {

			Player player = players.get(i);

			broadcast("\\player_data:" + player.getName() + ":" + player.getX() + ":" + player.getY() + ":"
					+ player.getZ() + ":" + player.getAge() + ":" + player.getHealth());

		}
	}

	public static void sendNewData(Player player) {

		for (Client client : clients) {

			if (!client.getName().equals(player.getName())) {

				send("\\player_update_data:" + player.getName() + ":" + player.getX() + ":" + player.getY() + ":"
						+ player.getZ() + ":" + player.getAge() + ":" + player.getHealth(), client.getAddress(),
						client.getPort());
				
				send("Data has been given by server.", client.getAddress(), client.getPort());
			}
		}
	}

}
