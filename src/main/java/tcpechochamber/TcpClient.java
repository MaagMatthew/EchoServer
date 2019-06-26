
package tcpechochamber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TcpClient {

	public static void main(String[] args) throws IOException {
		/*
		 * Client:Says a thing
		 * Display the thing 
		 * Send thing to server Client waits for response from server 
		 * When server responds, display what server said
		 */

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		exceptionCheck(args);
		
		
		Socket socket = new Socket("localhost", 27653);
		byte[] helloBuffer = new byte[200];

		socket.getInputStream().read(helloBuffer);
		String helloString = new String(helloBuffer).trim();
		System.out.println("Recieved from server '" + helloString + "'");

		if (!helloString.equals("hello")) {
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

	private static boolean validateIp(String ipAddress) {
		String[] bytes = ipAddress.split(".");
		if (bytes.length != 4)
			return false;
		try {
			for (String b : bytes) {
				int temp = Integer.parseInt(b);
				if (temp < 0 || temp > 255)
					return false;

			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private static void exceptionCheck(String[] args) {
		String exceptionMessage = "";
		if(!validateIp(args[0])){
			exceptionMessage += "Your IP Address was invalid. ";
		}
		if(!validatePort(args[1])) {
			exceptionMessage += "Your Port number was invalid. ";
		}
		if(exceptionMessage.length() == 0) throw new NumberFormatException(exceptionMessage);
		 
	}
	
	

}
