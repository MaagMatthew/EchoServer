package tcpechochamber;

import java.io.IOException;
import java.net.Socket;

public class TcpClient {

	public static void main(String[] args) throws IOException {

		//
		
		Socket socket = new Socket("localhost", 27653);
		byte[] helloBuffer = new byte[200];
		socket.getInputStream().read(helloBuffer);
		String helloString = new String(helloBuffer).trim();
		System.out.println("Recieved from server '" + helloString + "'");
		if (!helloString.equals("hello")) {
			throw new RuntimeException("bad Hello");
		}
		socket.getOutputStream().write(String.format("%200s", "date").getBytes());
		byte[] dateResponseBuffer = new byte[200];
		socket.getInputStream().read(dateResponseBuffer);
		String rawDateTime = new String(dateResponseBuffer).trim();
		long dateTime = Long.parseLong(rawDateTime);
		socket.close();

	}

}
