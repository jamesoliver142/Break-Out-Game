package breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Brick implements GameElement {
	
	public int topLeft;
	public int topRight;
	public int width;
	public int height;
	public Color colour;
	
	public Brick(int topLeft, int topRight, int width, int height, Color colour) {
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.width = width;
		this.height = height;
		this.colour = colour;
	}
	
	public void paintThis(Graphics g) {
		g.setColor(colour);
		g.fillRect(topLeft, topRight, width, height);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(topLeft, topRight, width, height);
	}
}
