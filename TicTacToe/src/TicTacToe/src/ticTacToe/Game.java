package ticTacToe;

import game.gui.GameWindow;
import game.gui.Window;

public class Game {
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static int FIELD_WIDTH = WIDTH / 3;
	public static int FIELD_HEIGHT = HEIGHT / 3;

	private Window window;
	private GameWindow gameWindow;
	
	public Game() {
		window = new Window ("Tic Tac Top", WIDTH, HEIGHT);
		gameWindow = new GameWindow();
		window.add(gameWindow);
		
	}
}
