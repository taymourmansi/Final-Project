import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws Exception {
		ServerSocket listener = new ServerSocket(8901);
		System.out.println("Server is Running");
		try {
			while (true) {
				Game game = new Game();
				Game.Player playerOne = game.new Player(listener.accept(), 'X');
				Game.Player playerTwo = game.new Player(listener.accept(), 'O');
				playerOne.setOpp(playerTwo);
				playerTwo.setOpp(playerOne);
				game.currentPlayer = playerOne;
				playerOne.start();
				playerTwo.start();
			}
		} finally {
			listener.close();
		}
	}
}




