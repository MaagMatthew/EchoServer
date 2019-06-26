
package tcpechochamber;

import java.io.IOException;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TcpClient {

	public static void main(String[] args) throws IOException {
		/*
		 * Client:Says a thing
		 * Display the thing
		 * Send thing to server
		 * Client waits for response from server
		 * When server responds, display what server said
		 */
		
		Socket socket = new Socket("localhost", 27653);
		byte[] helloBuffer = new byte[200];
		
		socket.getInputStream().read(helloBuffer);
		String helloString = new String(helloBuffer).trim();
		System.out.println("Recieved from server '" + helloString + "'");
		
		if(!helloString.equals("hello")) {
			throw new RuntimeException("bad hello");
		}
		
		socket.getOutputStream().write(String.format("%200s", "date").getBytes());
		
		byte[] dateResponseBuffer = new byte[200];
		socket.getInputStream().read(dateResponseBuffer);
		String rawDateTime = new String(dateResponseBuffer).trim();
		
		long dateTime = Long.parseLong(rawDateTime);
		LocalDateTime displayableTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault());
		
		System.out.println("Server time is '" + displayableTime + "'");
		socket.close();

	}
	
}
