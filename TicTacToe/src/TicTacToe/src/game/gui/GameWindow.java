package game.gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import ticTacToe.Game;

public class GameWindow extends JPanel{

	private static final long serialVersionUID = 1L;

	public GameWindow() {
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(10));
		
		for(int i = Game.FIELD_WIDTH ; i <= 2 * Game.FIELD_WIDTH; i += Game.FIELD_WIDTH) {
			g2D.drawLine(i, 0, i, Game.HEIGHT);
		}
		
		for(int i = Game.FIELD_HEIGHT ; i <= 2 * Game.FIELD_HEIGHT; i += Game.FIELD_HEIGHT) {
			g2D.drawLine(0, i, Game.WIDTH, i);
		}
		
	}

}
