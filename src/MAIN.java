/**
 * MAIN.java
 * @author Garrett J. Beasley
 * 11/01/2014
 * Main class for lunching Snake and Apple
 */

/** Imports for the project */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import acm.graphics.*;
import acm.program.GraphicsProgram;

public class MAIN extends GraphicsProgram
{
	
	/**Constant Window Size*/
	public static final int WINDOW_X = 1000, WINDOW_Y = 1000 ;
	private static final int SCORE_HEIGHT = 25, SCORE_WIDTH = 350;
	private static final int SNAKE_SIZE = 20, APPLE_SIZE = 15;
	int snakeMoveX = 0;
	int snakeMoveY = 1;
	
	/**Creates objects for global access throughout the class*/
	private GLabel scoringLabel;
	private int score;
	private Snake snake;
	private Apple apple;
	boolean continueGame = true;
	
	public void init()
	{
		/**Set the size of the applet*/
		setSize(WINDOW_X,WINDOW_Y);
		
		/**Set the color of the applet*/
		setBackground(Color.BLACK);
		
		/**Adds the key listern to watch for up and down keys being pressed*/
		addKeyListeners();
		
		/** add score label */
		scoringLabel = new GLabel("Score: 0", (WINDOW_X - SCORE_WIDTH) / 2,(WINDOW_X - SCORE_HEIGHT) / 2);	
		scoringLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 75));
		scoringLabel.setColor(Color.WHITE);
		add(scoringLabel);
	}
	
	
	
	public void run()
	{
		/**Stops the applet from running right away until its clicked and then after will animate the applet*/
		waitForClick();
		animation();
	}
	
	
	
	public void animation()
	{
		
		buildSnake();
		buildApple();
		
		/**While loop for the game animation*/
		while(continueGame)
		{
			/**Slows the loop by 20 seconds*/
			pause(20);
			snake.move(snakeMoveX,snakeMoveY);
			checkCollision();
			
		}
		
	}
	
	
	
	
	/**keypressed method called from the top in the init*/
	public void keyPressed(KeyEvent event)
	{
		/**Checks to see if the key pressed = the up key if it does enter the if statement*/
		if (event.getKeyCode() == KeyEvent.VK_UP) 
		{	
			snakeMoveY = -2;
			snakeMoveX = 0;
		}
		
		/**Checks to see if the key pressed = the down key if it does enter the if statement*/
		if (event.getKeyCode() == KeyEvent.VK_DOWN) 
		{
			snakeMoveY = 2;
			snakeMoveX = 0;
		}
		
		/**Checks to see if the key pressed = the down key if it does enter the if statement*/
		if (event.getKeyCode() == KeyEvent.VK_LEFT) 
		{
			snakeMoveX = -2;
			snakeMoveY = 0;
		}
		
		/**Checks to see if the key pressed = the down key if it does enter the if statement*/
		if (event.getKeyCode() == KeyEvent.VK_RIGHT) 
		{
			snakeMoveX = 2;
			snakeMoveY = 0;
		}
	}
	
	
	/**playerScored method will be called when the butterfly intersects with the net.*/
	public void playerScored() 
	{
		/**increment to score*/
		score++;
		
		/**display new score on screen*/
		scoringLabel.setLabel("Score: " + score);
	}
	
	public void buildSnake()
	{
		/**Creates new ball with set size passed in from the BALL_SIZE*/
		snake = new Snake(Color.GREEN, SNAKE_SIZE);
		
		/**Sets random location for the ball created withn the top half of the window to start*/
		snake.setLocation((int) (Math.random( )*(WINDOW_X-(SNAKE_SIZE))), (int) (Math.random( )*(WINDOW_Y/2-(SNAKE_SIZE))));
		
		/**Adds the platforms to the project*/
		add(snake);
	}
	
	public void buildApple()
	{
		/**Creates new ball with set size passed in from the BALL_SIZE*/
		apple = new Apple(Color.RED, APPLE_SIZE);
		
		/**Sets random location for the ball created withn the top half of the window to start*/
		apple.setLocation((int) (Math.random( )*(WINDOW_X-(APPLE_SIZE))), (int) (Math.random( )*(WINDOW_Y-(APPLE_SIZE))));
		
		/**Adds the platforms to the project*/
		add(apple);
	}
	
	public void checkCollision()
	{
		/**IF i platform intersects with the ball remove the gravity from the ball*/
		if (snake.getBounds().intersects(apple.getBounds()))
		{
			playerScored();
			
			/**Sets random location for the ball created withn the top half of the window to start*/
			apple.setLocation((int) (Math.random( )*(WINDOW_X-(APPLE_SIZE))), (int) (Math.random( )*(WINDOW_Y-(APPLE_SIZE))));	
			
		}
	}
	

}
