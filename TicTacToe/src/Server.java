import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(5555);
		System.out.println("Server is Running");
		try {
			final boolean work = true;
			while (work) {
				Game game = new Game();
				Game.Player playerOne = game.new Player(ss.accept(), 'X');
				Game.Player playerTwo = game.new Player(ss.accept(), 'O');
				playerOne.setOpp(playerTwo);
				playerTwo.setOpp(playerOne);
				game.currentPlayer = playerOne;
				playerOne.start();
				playerTwo.start();
			}
		} finally {
			ss.close();
		}
	}
}




