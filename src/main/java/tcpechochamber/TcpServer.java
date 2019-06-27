
package tcpechochamber;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
	private static ServerSocket serverSocket;

	public static void main(String[] args) throws IOException {
		/*
		 * Wait for message Read string from client display "received from client:"
		 * followed by message display "sending to client:" followed by message Send
		 * back to client continuous
		 */


		int port = Integer.parseInt(args[0]);
		while (validatePort(port)) {
			try {
				serverSocket = new ServerSocket(port);
				run(port);
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			throw new RuntimeException("Bad port");
	}

	public static boolean validatePort(int port) {
		return port > 1023 && port < 65535;
	}

	private static void run(int port) throws IOException {
		Socket socket = serverSocket.accept();
		
		System.out.println("Connected to client");
		
		byte[] hello = String.format("%200s", "What would you like me to echo?").getBytes();
		System.out.println("Sending message to client");
		socket.getOutputStream().write(hello);
		System.out.println("Message sent to client. Waiting for response...");

		byte[] echoMessage = new byte[200];
		socket.getInputStream().read(echoMessage);

		String echoString = new String(echoMessage).trim();
		System.out.println("Recieved from client: " + echoString.trim());

		if (echoString.isEmpty() || echoString == null) {
			throw new RuntimeException("Bad request");
		}
		System.out.println("Sending to client: " + echoString.trim());

		String response = echoString;
		byte[] responseBuffer = String.format("%200s", response).getBytes();

		socket.getOutputStream().write(responseBuffer);
		socket.close();
	}

}
