import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Listener implements Runnable {

	private Socket connectSock = null;
	private boolean myTurn;

	Listener(Socket s) {
		this.connectSock = s;
	}

	public void run() {
		try {
			BufferedReader serverIn = new BufferedReader(new InputStreamReader(connectSock.getInputStream()));
			while (true) {
				if (serverIn == null) {
					System.out.println("Closing connection for socket " + connectSock);
					connectSock.close();
					break;
				}

				String serverString = serverIn.readLine();

				if (serverString.startsWith("#")) {
					printBoard(serverString.substring(1));
				} else if (serverString.startsWith("~")) {
				} else if (serverString.startsWith("+")) {
					this.myTurn = true;
				} else if (serverString.startsWith("-")) {
					this.myTurn = false;
				} else {
					System.out.println(serverString);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printBoard(String s) {
		String[] rows = s.split(";");
		String[][] board = new String[3][3];

		for (int i = 0; i < 3; i++) {
			board[i] = rows[i].split(",");
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j].equals("1")) {
					board[i][j] = "X";
				} else if (board[i][j].equals("-1")) {
					board[i][j] = "O";
				} else {
					board[i][j] = " ";
				}
			}
		}

		System.out.println("   0   1   2");
		System.out.format("0 %2s |%2s |%2s \n", board[0][0], board[0][1], board[0][2]);
		System.out.println("  ---|---|---");
		System.out.format("1 %2s |%2s |%2s \n", board[1][0], board[1][1], board[1][2]);
		System.out.println("  ---|---|---");
		System.out.format("2 %2s |%2s |%2s \n", board[2][0], board[2][1], board[2][2]);

	}

}
