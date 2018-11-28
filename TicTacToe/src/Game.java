import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class Game {
	private Player[] board = { null, null, null, null, null, null, null, null, null };
	Player currentPlayer;

	public boolean winner() {
		return (board[0] != null && board[0] == board[1] && board[0] == board[2])
				|| (board[3] != null && board[3] == board[4] && board[3] == board[5])
				|| (board[6] != null && board[6] == board[7] && board[6] == board[8])
				|| (board[0] != null && board[0] == board[3] && board[0] == board[6])
				|| (board[1] != null && board[1] == board[4] && board[1] == board[7])
				|| (board[2] != null && board[2] == board[5] && board[2] == board[8])
				|| (board[0] != null && board[0] == board[4] && board[0] == board[8])
				|| (board[2] != null && board[2] == board[4] && board[2] == board[6]);
	}

	public boolean fullBoard() {
		for (int i = 0; i < board.length; i++) {
			if (board[i] == null) {
				return false;
			}
		}
		return true;
	}

	public synchronized boolean validMove(int position, Player player) {
		if (player == currentPlayer && board[position] == null) {
			board[position] = currentPlayer;
			currentPlayer = currentPlayer.opp;
			currentPlayer.otherPlayerMoved(position);
			return true;
		}
		return false;
	}

	class Player extends Thread {
		char shape;
		Player opp;
		Socket socket;
		BufferedReader input;
		PrintWriter output;

		public Player(Socket socket, char shape) {
			this.socket = socket;
			this.shape = shape;
			try {
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
				output.println("WELCOME " + shape);
				output.println("MESSAGE Waiting for opponent to connect");
			} catch (IOException e) {
				System.out.println("Player left: " + e);
			}
		}

		public void setOpp(Player opp) {
			this.opp = opp;
		}


		public void otherPlayerMoved(int position) {
			output.println("OPPONENT_MOVED " + position);
			output.println(winner() ? "DEFEAT" : fullBoard() ? "TIE" : "");
		}

		public void run() {
			try {
				output.println("MESSAGE All players connected");

				if (shape == 'X') {
					output.println("MESSAGE Your move");
				}

				while (true) {
					String str = input.readLine();
					if (str.startsWith("MOVE")) {
						int location = Integer.parseInt(str.substring(5));
						if (validMove(location, this)) {
							output.println("VALID_MOVE");
							output.println(winner() ? "VICTORY" : fullBoard() ? "TIE" : "");
						} else {
							output.println("MESSAGE ?");
						}
					} else if (str.startsWith("QUIT")) {
						return;
					}
				}
			} catch (IOException e) {
				System.out.println("Player left: " + e);
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
