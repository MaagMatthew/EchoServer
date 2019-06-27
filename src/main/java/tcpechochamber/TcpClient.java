
package tcpechochamber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TcpClient {

	public static void main(String[] args) {
		/*
		 * Client:Says a thing Display the thing Send thing to server Client waits for
		 * response from server When server responds, display what server said
		 */
		exceptionCheck(args);
		run(args);
	}

	private static void run(String[] args) {
	
		Socket socket = establishConnection(args);
	
		System.out.println(waitForResponse(socket));
	
		sendResponseToBeEchoed(socket);
	
		System.out.println("Server says: " + waitForResponse(socket));
	
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Socket establishConnection(String[] args) {
		Socket socket = null;
		try {
			socket = new Socket(args[0], Integer.parseInt(args[1]));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connected");
		return socket;
	}

	private static String waitForResponse(Socket socket) {
		byte[] receptionBuffer = new byte[200];
		try {
			socket.getInputStream().read(receptionBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(receptionBuffer).trim();
	}

	private static void sendResponseToBeEchoed(Socket socket) {
		String sendToServer;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			sendToServer = in.readLine();
			socket.getOutputStream().write(String.format("%200s", sendToServer).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean validateIp(String ipAddress) {
		String[] bytes = ipAddress.split("\\.");
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
		if (args.length < 2)
			throw new IllegalArgumentException("Arguments required: IP Port");
		if (!validateIp(args[0])) {
			exceptionMessage += "Your IP Address was invalid. ";
		}
		if (!TcpServer.validatePort(Integer.parseInt(args[1]))) {
			exceptionMessage += "Your Port number was invalid. ";
		}
		if (exceptionMessage.length() != 0)
			throw new NumberFormatException(exceptionMessage);

	}

}
