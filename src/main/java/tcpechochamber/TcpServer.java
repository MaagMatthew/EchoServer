package tcpechochamber;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = new ServerSocket(27653);
		Socket socket = serverSocket.accept();
		byte[] hello = String.format("%200s", "hello").getBytes();
		socket.getOutputStream().write(hello);
		byte[] question = new byte[200];
		socket.getInputStream().read(question);
		String questionString = new String(question).trim();
		if(!questionString.contains("date")) {
			throw new RuntimeException("Bad request");
		}
		long currentDateTime = System.currentTimeMillis();
		byte[] dateResponse = String.format("%200d", currentDateTime).getBytes();
		socket.getOutputStream().write(dateResponse);
		
		
		
	}
	
}
