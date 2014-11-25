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
	private static final int GAMEOVER_HEIGHT = -100, GAMEOVER_WIDTH = 275;
	private static final int SNAKE_SIZE = 20, APPLE_SIZE = 15, ENEMYSNAKE_SIZE = 40;
	int snakeMoveX = 0; int keyMoveSpeedX = 0, keyMoveSpeedY = 0;
	int snakeMoveY = 1;
	int snakeBodyCount = 1;
	
	
	/**Creates objects for global access throughout the class*/
	private GLabel scoringLabel, gameover;
	private int score;
	private Snake[] snake;
	private Snake enemySnake;
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
		scoringLabel = new GLabel("Level: 0", (WINDOW_X - SCORE_WIDTH) / 2,(WINDOW_Y - SCORE_HEIGHT) / 2);	
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
		enemyBuild();
		
		/**While loop for the game animation*/
		while(continueGame)
		{
			/**Slows the loop by 20 seconds*/
			pause(20);
			/**Loop inside the animation loop to detect how long the platform arry is and loop that many times to create all 3 platforms*/
			for (int i = 0; i < snake.length; i++) 
			{
				snake[i].move(snakeMoveX,snakeMoveY);
	
			}
			checkCollision();
			wallCheck();
			//enemyAttack();
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
		
		  /**Loop inside the animation loop to detect how long the platform arry is and loop that many times to create all 3 platforms*/
	    for (int i = 0; i < snake.length; i++) 
	    {
	        snake[i].removeAll();
	    }

		snakeBodyCount++;
		buildSnake();
	}
	
	/**playerScored method will be called when the butterfly intersects with the net.*/
	public void playerLost() 
	{
		/**increment to score*/
		score--;
		
		/**display new score on screen*/
		scoringLabel.setLabel("Score: " + score);
		
		endGame();
	}
	
	public void buildSnake()
	{
		
		/**builds new platforms based off the number of platforms passed in*/
		snake = new Snake [snakeBodyCount];
		
		/**Loops the platforms for the amount of platforms passed in (3)*/
		for (int i = 0; i <  snakeBodyCount; i++) 
		{
		
			/**Creates new ball with set size passed in from the BALL_SIZE*/
			snake[i] = new Snake(Color.GREEN, SNAKE_SIZE);
			
			/**Sets random location for the ball created withn the top half of the window to start*/
			snake[0].setLocation(WINDOW_X/2,WINDOW_Y/2);
			snake[i].setLocation(snake[0].getX()+(SNAKE_SIZE*i),snake[0].getY());
			/**Adds the platforms to the project*/
			add(snake[i]);
		}
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
		/**Loop inside the animation loop to detect how long the platform arry is and loop that many times to create all 3 platforms*/
		for (int i = 0; i < snake.length; i++) 
		{
			/**IF i platform intersects with the ball remove the gravity from the ball*/
			if (snake[i].getBounds().intersects(apple.getBounds()))
			{
				playerScored();
				
				/**Sets random location for the ball created withn the top half of the window to start*/
				apple.setLocation((int) (Math.random( )*(WINDOW_X-(APPLE_SIZE))), (int) (Math.random( )*(WINDOW_Y-(APPLE_SIZE))));	
				
			}
			
			/**IF i platform intersects with the ball remove the gravity from the ball*/
			if (snake[i].getBounds().intersects(enemySnake.getBounds()))
			{	
				playerLost();
			}
		}
	}
	
	public void wallCheck()
	{
		/**Checks the ball to see if it has hit the bottom*/
		if (snake[0].getY() >= WINDOW_Y)
		{
			playerLost();
		}
		
		/**Checks the ball to see if it has hit the top*/
		if (snake[0].getY()  <= 0)
		{
			playerLost();
		}
		
		/**Checks the ball to keep it in the applet*/
		if (snake[0].getX()  <= 0)
		{
			playerLost();
		}
		
		/**Checks the ball to keep it in the applet*/
		if (snake[0].getX() >= WINDOW_X)
		{
			playerLost();
		}
	}
	
	public void endGame()
	{
		/** add score label */
		gameover = new GLabel("Game Over", (WINDOW_X - GAMEOVER_WIDTH) / 2,(WINDOW_Y - GAMEOVER_HEIGHT)/2);	
		gameover.setFont(new Font("Comic Sans MS", Font.BOLD, 45));
		gameover.setColor(Color.WHITE);
		add(gameover);
		
		continueGame = false;
	}
	
	public void enemyBuild()
	{
		/**Creates new ball with set size passed in from the BALL_SIZE*/
		enemySnake = new Snake(Color.BLUE, ENEMYSNAKE_SIZE);
		
		/**Sets random location for the ball created withn the top half of the window to start*/
		enemySnake.setLocation((int) (Math.random( )*(WINDOW_X-(ENEMYSNAKE_SIZE))), (int) (Math.random( )*(WINDOW_Y-(ENEMYSNAKE_SIZE))));
		
		/**Adds the platforms to the project*/
		add(enemySnake);
	}
	
	public void enemyAttack()
	{
		
		enemySnake.setLocation((int) (Math.random( )*(WINDOW_X-(ENEMYSNAKE_SIZE))), (int) (Math.random( )*(WINDOW_Y-(ENEMYSNAKE_SIZE))));
	}

}
