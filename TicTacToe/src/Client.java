import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Client {

	private JFrame frame = new JFrame("Tic Tac Toe");
	private JLabel msgLbl = new JLabel("");
	private ImageIcon img;
	private ImageIcon oppImg;

	private Box[] gameBoard = new Box[9];
	private Box currSquare;

	private static int PORT = 5555;
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private boolean win = false;
	private boolean lose = false;
	private boolean tie = false;

	public Client(String address) throws Exception {

		socket = new Socket(address, PORT);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);

		msgLbl.setBackground(Color.white);
		frame.getContentPane().add(msgLbl, "South");

		JPanel boardPanel = new JPanel();
		boardPanel.setBackground(Color.black);
		boardPanel.setLayout(new GridLayout(3, 3, 2, 2));
		for (int i = 0; i < gameBoard.length; i++) {
			final int j = i;
			gameBoard[i] = new Box();
			gameBoard[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					currSquare = gameBoard[j];
					output.println("MOVE " + j);
				}
			});
			boardPanel.add(gameBoard[i]);
		}
		frame.getContentPane().add(boardPanel, "Center");
	}

	public void play() throws Exception {
		String response;
		try {
			response = input.readLine();
			if (response.startsWith("WELCOME")) {
				char shape = response.charAt(8);
				img = new ImageIcon(shape == 'X' ? "x.png" : "o.png");
				oppImg = new ImageIcon(shape == 'X' ? "o.png" : "x.png");
				frame.setTitle("Player " + shape);
			}
			final boolean t = true;
			while (t) {
				response = input.readLine();
				if (response.startsWith("VALID_MOVE")) {
					msgLbl.setText("Waiting for Opponent");
					currSquare.setIcon(img);
					currSquare.repaint();
				} else if (response.startsWith("OPPONENT_MOVED")) {
					int space = Integer.parseInt(response.substring(15));
					gameBoard[space].setIcon(oppImg);
					gameBoard[space].repaint();
					msgLbl.setText("It's your turn");
				} else if (response.startsWith("VICTORY")) {
					win = true;
					msgLbl.setText("Winner!");
					break;
				} else if (response.startsWith("DEFEAT")) {
					lose = true;
					msgLbl.setText("LOSER!");
					break;
				} else if (response.startsWith("TIE")) {
					tie = true;
					msgLbl.setText("Tie");
					break;
				} else if (response.startsWith("MESSAGE")) {
					msgLbl.setText(response.substring(8));
				}
			}
			output.println("QUIT");
		} finally {
			socket.close();
		}
	}

	private boolean rematch() {
		if (win == true) {
			int response = JOptionPane.showConfirmDialog(frame, "Play Again?", "You Win!", JOptionPane.YES_NO_OPTION);
			frame.dispose();
			return response == JOptionPane.YES_OPTION;

		} else if (lose == true) {
			int response = JOptionPane.showConfirmDialog(frame, "Play Again?", "You Lose!", JOptionPane.YES_NO_OPTION);
			frame.dispose();
			return response == JOptionPane.YES_OPTION;

		} else {
			int response = JOptionPane.showConfirmDialog(frame, "Play Again?", "You Tied!", JOptionPane.YES_NO_OPTION);
			frame.dispose();
			return response == JOptionPane.YES_OPTION;

		}

	}

	static class Box extends JPanel {
		private static final long serialVersionUID = 1L;
		JLabel label = new JLabel((Icon) null);

		public Box() {
			setBackground(Color.white);
			add(label);
		}

		public void setIcon(Icon img) {
			label.setIcon(img);
		}
	}

	public static void main(String[] args) throws Exception {
		boolean control = true;
		while (control) {
			String address = (args.length == 0) ? "localhost" : args[1];
			Client client = new Client(address);
			client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.frame.setResizable(false);
			client.frame.setVisible(true);
			client.frame.setSize(500, 500);
			client.play();
			if (!client.rematch()) {
				break;
			}
		}
	}
}
