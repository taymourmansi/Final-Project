import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {
			String host = "localhost";
			int port = 55555;
			boolean myTurn = true;
			System.out.println("Connecting to game server on port " + port);
			Socket connectSock = new Socket(host, port);
			DataOutputStream serverOut = new DataOutputStream(connectSock.getOutputStream());
			System.out.println("Connected!");

			Listener listener = new Listener(connectSock);
			Thread thread = new Thread(listener);
			thread.start();
			Scanner scan = new Scanner(System.in);

			while (serverOut != null) {
				String line = scan.nextLine();
				if (!myTurn) {
					System.out.println("It is not your turn.");
				} else if ((line.equals("0") || line.equals("1")) || line.equals("2")) {
					serverOut.writeBytes(line + "\n");
				} else if (line.equals("quit")) {
					serverOut.close();
					serverOut = null;
				} else {
					System.out.println("Please enter either 0,1,2, or 'quit'");
				}
			}
			System.out.println("No connection.");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
