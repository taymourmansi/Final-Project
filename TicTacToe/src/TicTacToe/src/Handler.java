import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Handler implements Runnable {

	public Socket s;
	public Socket[] sockets;
	public Basics game;
	public int player;

	public Handler(Socket s, Socket[] sockets, Basics game, int player) {
		this.s = s;
		this.sockets = sockets;
		this.game = game;
		this.player = player;
	}

	@Override
	public void run() {

		try {

			BufferedReader input = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
			switch (this.player) {
			case -1:
				sendString("\nYou are player 'o', you go second" + "\r\n");
				sendString("-" + "\r\n");
				break;

			case 1:
				sendString("\nYou are player 'x', you go first" + "\r\n");
				sendString("-" + "\r\n");
				break;

			default:
				break;
			}

			while (game.win() == 0) {
				sendString(game.printBoard() + "\r\n");
				String play = "";
				int playCount = 1;
				int inverse = 0;

				if (game.move == player) {
					sendString("Pleaae enter a row (0-2): " + "\r\n");
					String row = input.readLine().trim();
					sendString("Pleaae enter a column (0-2): " + "\r\n");
					String col = input.readLine().trim();

					if (!(game.validMove(Integer.parseInt(row), Integer.parseInt(col)))) {
						sendString("move not valid." + "\r\n");
					} else {
						sendString("-" + "\r\n");
					}
				} else {
					sendString("Wait for other player's move");
					while (game.move != player) {
						Thread.sleep(600);
					}
					sendString("+" + "\r\n");

				}

			}

			sendString(game.printBoard());

			int whoWon = game.win();
			sendString(Integer.toString(whoWon) + "\r\n");

			if (whoWon == player) {
				sendString("YOU WIN!" + "\r\n");
			} else if (whoWon == 2) {
				sendString("TIE GAME!" + "\r\n");
			} else {
				sendString("YOU LOSE");
			}

		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		} catch (InterruptedException e1) {
			System.out.println(e1.getStackTrace());
		}

	}

	private void sendString(String send) {
		try {
			DataOutputStream playerOut = new DataOutputStream(this.s.getOutputStream());
			playerOut.writeBytes(send);
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}

}
