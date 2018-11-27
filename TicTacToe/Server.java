import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

	private Socket[] sockets;

	public Server() {
		this.sockets = new Socket[2];
	}

	public void connect() {
		try {
			System.out.println("waiting for player on port 5555.");
			ServerSocket ss = new ServerSocket(5555);

			Basics game = new Basics();

			int player = 1;

			for (int i = 0; i < 2; ++i) {
				Socket sock = ss.accept();
				// Add this socket to the list
				this.sockets[i] = sock;
				// Send to ClientHandler the socket and arraylist of all sockets

				System.out.println("Player " + Integer.toString(i + 1) + " connected successfully.");

				Handler handler = new Handler(sock, this.sockets, game, player);
				Thread thread = new Thread(handler);
				thread.start();
				player -= 2;
			}

			System.out.println("Game starting!");

			Socket sock = ss.accept();

			for (int i = 0; i < sockets.length; ++i) {
				sockets[i].close();
			}

		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}

	}

	public static void main(String[] args) {
		Server server = new Server();
		server.connect();
	}

}
