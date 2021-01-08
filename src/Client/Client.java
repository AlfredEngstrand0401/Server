package Client;

import java.net.InetAddress;

public class Client {
	
	private InetAddress address;
	int port;
	private String name;
	
	public Client(String name, InetAddress address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;
	}

	public InetAddress getAddress() {
		return address;
	}

	public String getName() {
		return name;
	}
	
	public int getPort() {
		return port;
	}
}
