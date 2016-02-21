package breakout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PaintingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int FONT_SIZE = 48;

	public Vector<GameElement> contents;
	public boolean isLoser;
	public boolean isWinner;
	
	public PaintingPanel(Vector<GameElement> contents) {
		this.contents = contents;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(GameElement element : contents) {
			element.paintThis(g);
		}
		if (isLoser) {
			g.setColor(Color.YELLOW);
			Font font = new Font("Serif", Font.BOLD, FONT_SIZE);
			g.setFont(font);
			int width = g.getFontMetrics().stringWidth("You Lose!");
			g.drawString("You Lose!", (getWidth()-width)/2, getHeight()/2);
		}
		
		if (isWinner) {
			g.setColor(Color.YELLOW);
			Font font = new Font("Serif", Font.BOLD, FONT_SIZE);
			g.setFont(font);
			int width = g.getFontMetrics().stringWidth("You Win!");
			g.drawString("You Win!", (getWidth()-width)/2, getHeight()/2);
		}
	}
}
