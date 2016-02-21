package breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paddle implements GameElement {

	public static final int PADDLE_SIZE = 100;
	public static final int PADDLE_THICKNESS = 20;
	public int centreX;
	public int centreY;

	public void paintThis(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(centreX - PADDLE_SIZE / 2,
				centreY - PADDLE_THICKNESS / 2,
				PADDLE_SIZE, PADDLE_THICKNESS);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(centreX - PADDLE_SIZE / 2,
				centreY - PADDLE_THICKNESS / 2, 
				PADDLE_SIZE, PADDLE_THICKNESS);
	}
}
