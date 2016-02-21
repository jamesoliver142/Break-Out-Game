
package breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball implements GameElement {
	
	private static final int BALL_SPEED = 10;
	
	public int x;
	public int y;
	public int radius;
	public Color colour;
	public double dirX;
	public double dirY;
	public int speed;
	public boolean isActive;
	
	public Ball(int x, int y, int radius, Color colour) {
		this.radius = radius;
		this.colour = colour;
		this.x = x;
		this.y = y;
		Random random = new Random();
		double number = random.nextDouble();
		double abs = Math.sqrt(Math.pow(number, 2) +
				Math.pow(1.0-number, 2));
		dirX = number / abs;
		dirY = (1.0-number) / abs;
		speed = BALL_SPEED;
		isActive = true;
	}
	
	public void paintThis(Graphics g) {
		g.setColor(colour);
		g.fillOval(x - radius, y - radius, 2*radius, 2*radius);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x - radius, y - radius, 2*radius, 2*radius);
	}
	
	public void move() {
		x -= dirX * speed;
		y -= dirY * speed;
	}
	
	public void reflect(boolean verticle, boolean horizontal) {
		if (verticle) dirX *= -1;
		if (horizontal) dirY *= -1;
	}
}
