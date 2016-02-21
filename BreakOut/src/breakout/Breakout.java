
package breakout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Breakout extends TimerTask {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int PADDLE_DISTANCE_FROM_BOTTOM = 50;
	private static final int NUMBER_OF_ROWS = 8;
	private static final int NUMBER_OF_COLS = 13;
	private static final int BRICK_WIDTH = 50;
	private static final int BRICK_HEIGHT = 20;
	private static final int BRICK_GAP = 10;
	private static final int BRICK_MARGIN = 15;
	private static final int ORANGE_BRICK_START_ROW = 1;
	private static final int ORANGE_BRICK_END_ROW = 4;
	private static final int BALL_RADIUS = 5;
	private static final int PADDLE_SPEED = 20;

	public Vector<Brick> bricks;
	public Vector<Ball> balls;
	public Paddle paddle;
	public PaintingPanel paintingPanel;
	public boolean gameRunning = false;
	private boolean isInitialized = false;

	public Breakout() {
		intializeGame();
		showGUI(paintingPanel, this);
	}

	private void intializeGame() {
		Random random = new Random();
		Vector<GameElement> contents = new Vector<GameElement>();
		
		bricks = new Vector<Brick>();

		paddle = new Paddle();
		paddle.centreX = WIDTH / 2;
		paddle.centreY = HEIGHT - PADDLE_DISTANCE_FROM_BOTTOM;
		contents.add(paddle);
		
		int specialBrick = random.nextInt(104) + 1;
		
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int col = 0; col < NUMBER_OF_COLS; col++) {
				Color colour = Color.BLUE;
				if (row >= ORANGE_BRICK_START_ROW && row <= ORANGE_BRICK_END_ROW)
					colour = Color.ORANGE;
				else if (row > ORANGE_BRICK_END_ROW) {
					colour = Color.GREEN;
				}
				Brick brick = new Brick(col * (BRICK_WIDTH + BRICK_GAP) + BRICK_MARGIN,
						row * (BRICK_HEIGHT + BRICK_GAP) + BRICK_MARGIN, BRICK_WIDTH, BRICK_HEIGHT, colour);
				bricks.add(brick);
				if (bricks.size() == specialBrick) {
					brick.colour = Color.RED;
				}
				contents.add(brick);
			}
		}
		
		if (paintingPanel == null) {
			paintingPanel = new PaintingPanel(contents);
			paintingPanel.setBackground(Color.BLACK);
			paintingPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		} else {
			paintingPanel.contents = contents;
		}
		
		paintingPanel.isLoser = false;
		paintingPanel.isWinner = false;
		balls = new Vector<Ball>();
		
		isInitialized = true;
		gameRunning = false;
	}

	private void addBall(int numberOfBalls) {
		while(numberOfBalls > 0) {
			
			new Thread(new Runnable(){

				@Override
				public void run() {
					Ball ball = new Ball(WIDTH / 2, HEIGHT / 2, BALL_RADIUS, Color.WHITE);
					balls.add(ball);
					paintingPanel.contents.add(ball);
					while(ball.isActive) {
						ball.move();
						try {
							Thread.sleep(40);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
					}
				}
				
			}).start();
			
			numberOfBalls--;
		}
		
	}

	public void handleCollisions() {
		if (bricks.size() == 0) {
			stopGame();
			paintingPanel.isWinner = true;
		}
		boolean isSpecialBrickHit = false;
		Vector<Ball> ballsTemp = new Vector<Ball>();
		for (Ball ball : balls) {
			if (ball.y > HEIGHT) {
				ball.isActive = false;
				ballsTemp.add(ball);
				paintingPanel.contents.remove(ball);
			} else if (ball.x < 0 || ball.x > WIDTH)
				ball.reflect(true, false);
			else if (ball.y < 0)
				ball.reflect(false, true);
			else {
				for (Brick brick : bricks) {
					if (ball.getBounds().intersects(brick.getBounds())) {
						ball.reflect(false, true);
						bricks.remove(brick);
						paintingPanel.contents.remove(brick);
						if (brick.colour == Color.RED) {
							isSpecialBrickHit = true;
						}
						break;
					}
				}
				if (ball.getBounds().intersects(paddle.getBounds())) {
					ball.reflect(false, true);
				}
			}
		}
		
		for (Ball ball : ballsTemp) {
			balls.remove(ball);
		}
		
		if (balls.size() == 0) {
			stopGame();
			paintingPanel.isLoser = true;
		}
		
		if (isSpecialBrickHit) {
			addBall(2);
		}
	}

	public void stopGame() {
		for (Ball ball : balls) {
			ball.isActive = false;
		}
		gameRunning = false;
		isInitialized = false;
	}

	public void beginGame() {
		if (!isInitialized) {
			intializeGame();			
		}
		gameRunning = true;
		addBall(1);
	}

	@Override
	public void run() {
		if (gameRunning) {
			handleCollisions();
			paintingPanel.repaint();
		}
	}

	private static void showGUI(JPanel panel, Breakout breakout) {
		JFrame frame = new JFrame("Breakout");
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setResizable(false);
		frame.addKeyListener(breakout.new KeyBoardHandler());
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		frame.setLocation((int) (toolkit.getScreenSize().getWidth() - WIDTH) / 2,
				(int) (toolkit.getScreenSize().getHeight() - HEIGHT) / 2);
		frame.pack();
		frame.setVisible(true);
	}
	
	private class KeyBoardHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				paddle.centreX -= PADDLE_SPEED;
				break;
			case KeyEvent.VK_RIGHT:
				paddle.centreX += PADDLE_SPEED;
				break;
			case KeyEvent.VK_SPACE:
				if(!gameRunning) {
					beginGame();
				} else {
					stopGame();
					beginGame();
				}
				break;
			}			
		}
	}


	public static void main(String[] args) {
		Timer t = new Timer();
		t.schedule(new Breakout(), 0, 40);
	}

}
