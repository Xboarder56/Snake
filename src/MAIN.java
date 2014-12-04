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
	private static final int SNAKE_SIZE = 20, APPLE_SIZE = 15, ENEMYSNAKE_SIZE = 35;
	int snakeMoveX = 3; int keyMoveSpeedX = 0, keyMoveSpeedY = 0;
	int snakeMoveY = 0;
	int setSnakeMove = 3;
	int enemysnakeMoveY = 8, enemysnakeMoveX = 10, enemySnakeRand = 10; 
	int snakeBodyCount = 1;
	
	
	/**Creates objects for global access throughout the class*/
	private GLabel scoringLabel, gameover, instructionLabel, instructionLabel2, instructionLabel3, instructionLabel4;
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
		
		instructions();
	}
	
	
	
	public void run()
	{
		/**Stops the applet from running right away until its clicked and then after will animate the applet*/
		waitForClick();
		remove(instructionLabel);
		remove(instructionLabel2);
		remove(instructionLabel3);
		remove(instructionLabel4);
		animation();
	}
	
	public void instructions()
	{
		/**Create the labels with the different instructions*/
		instructionLabel = new GLabel("Snake, Click in the window to start.", (WINDOW_X - SCORE_WIDTH) / 2,30);
		instructionLabel2 = new GLabel("Eat the apple to advance to the next level.", (WINDOW_X - SCORE_WIDTH) / 2,60);	
		instructionLabel3 = new GLabel("Hit the wall and you lose!", (WINDOW_X - SCORE_WIDTH) / 2,90);	
		instructionLabel4 = new GLabel("Blue enemys attacking you will also lose the game", (WINDOW_X - SCORE_WIDTH) / 2,120);	
		
		/**Add the properties to the current labels*/
		instructionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		instructionLabel.setColor(Color.WHITE);
		instructionLabel2.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		instructionLabel2.setColor(Color.WHITE);
		instructionLabel3.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		instructionLabel3.setColor(Color.WHITE);
		instructionLabel4.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		instructionLabel4.setColor(Color.WHITE);
		
		/**Addes the instructions to the first screen*/
		add(instructionLabel);
		add(instructionLabel2);
		add(instructionLabel3);
		add(instructionLabel4);
	}
	
	public void animation()
	{
		/**Build the methods*/
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
			enemyAttack();
		}
		
	}
	
	
	
	
	/**keypressed method called from the top in the init*/
	public void keyPressed(KeyEvent event)
	{
		/**Checks to see if the key pressed = the up key if it does enter the if statement*/
		if (event.getKeyCode() == KeyEvent.VK_UP) 
		{	
			snakeMoveY = -setSnakeMove;
			snakeMoveX = 0;
		}
		
		/**Checks to see if the key pressed = the down key if it does enter the if statement*/
		if (event.getKeyCode() == KeyEvent.VK_DOWN) 
		{
			snakeMoveY = setSnakeMove;
			snakeMoveX = 0;
		}
		
		/**Checks to see if the key pressed = the down key if it does enter the if statement*/
		if (event.getKeyCode() == KeyEvent.VK_LEFT) 
		{
			snakeMoveX = -setSnakeMove;
			snakeMoveY = 0;
		}
		
		/**Checks to see if the key pressed = the down key if it does enter the if statement*/
		if (event.getKeyCode() == KeyEvent.VK_RIGHT) 
		{
			snakeMoveX = setSnakeMove;
			snakeMoveY = 0;
		}
	}
	
	
	/**playerScored method will be called when the butterfly intersects with the net.*/
	public void playerScored() 
	{
		/**increment to score*/
		score++;
		
		/**display new score on screen*/
		scoringLabel.setLabel("Level: " + score);
		
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
		
		/**display new score on screen*/
		scoringLabel.setLabel("Level: " + score);
		
		endGame();
	}
	
	public void buildSnake()
	{
		
		/**builds new platforms based off the number of platforms passed in*/
		snake = new Snake [snakeBodyCount];
		
		/**Loops the platforms for the amount of platforms passed in (3)*/
		for (int i = 0; i <  snakeBodyCount; i++) 
		{
		
			/**Creates new snake with set size passed in from the snake_SIZE*/
			snake[i] = new Snake(Color.GREEN, SNAKE_SIZE);
			
			/**Sets random location for the snake created withn the top half of the window to start*/
			snake[0].setLocation(WINDOW_X/2,WINDOW_Y/2);
			snake[i].setLocation(snake[0].getX()+(SNAKE_SIZE*i),snake[0].getY());
			/**Adds the platforms to the project*/
			add(snake[i]);
		}
	}
	
	public void buildApple()
	{
		/**Creates new snake with set size passed in from the snake_SIZE*/
		apple = new Apple(Color.RED, APPLE_SIZE);
		
		/**Sets random location for the snake created withn the top half of the window to start*/
		apple.setLocation((int) (Math.random( )*(WINDOW_X-(APPLE_SIZE))), (int) (Math.random( )*(WINDOW_Y-(APPLE_SIZE))));
		
		/**Adds the platforms to the project*/
		add(apple);
	}
	
	public void checkCollision()
	{
		/**Loop inside the animation loop to detect how long the platform array is and loop that many times to create all 3 platforms*/
		for (int i = 0; i < snake.length; i++) 
		{
			/**IF i platform intersects with the snake remove the gravity from the snake*/
			if (snake[i].getBounds().intersects(apple.getBounds()))
			{
				playerScored();
				
				/**Sets random location for the snake created withn the top half of the window to start*/
				apple.setLocation((int) (Math.random( )*(WINDOW_X-(APPLE_SIZE))), (int) (Math.random( )*(WINDOW_Y-(APPLE_SIZE))));	
				
			}
			
			/**IF i platform intersects with the snake remove the gravity from the snake*/
			if (snake[i].getBounds().intersects(enemySnake.getBounds()))
			{	
				playerLost();
			}
			
		}
	}
	
	public void wallCheck()
	{
		/**Loop inside the animation loop to detect how long the platform array is and loop that many times to create all 3 platforms*/
		for (int i = 0; i < snake.length; i++) 
		{
			/**Checks the snake to see if it has hit the bottom*/
			if (snake[i].getY() >= WINDOW_Y)
			{
				playerLost();
			}
			
			/**Checks the snake to see if it has hit the top*/
			if (snake[i].getY()  <= 0)
			{
				playerLost();
			}
			
			/**Checks the snake to keep it in the applet*/
			if (snake[i].getX()  <= 0)
			{
				playerLost();
			}
			
			/**Checks the snake to keep it in the applet*/
			if (snake[i].getX() >= WINDOW_X)
			{
				playerLost();
			}
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
		/**Creates new  with set size passed in from the snake_SIZE*/
		enemySnake = new Snake(Color.BLUE, ENEMYSNAKE_SIZE);
		
		/**Sets random location for the snake created withn the top half of the window to start*/
		enemySnake.setLocation((int) (Math.random( )*(WINDOW_X-(ENEMYSNAKE_SIZE))), (int) (Math.random( )*(WINDOW_Y-(ENEMYSNAKE_SIZE))));
		
		/**Adds the snake to the project*/
		add(enemySnake);
	}
	
	public void enemyAttack()
	{
		enemySnake.move(enemysnakeMoveX, enemysnakeMoveY);

		/**Checks the snake to see if it has hit the bottom*/
		if (enemySnake.getY() + enemySnake.getHeight() >= WINDOW_Y)
		{
			 enemysnakeMoveY=(int) (Math.random( )*-enemySnakeRand);
			enemySnake.move(enemysnakeMoveX, enemysnakeMoveY);
		}
		
		/**Checks the snake to see if it has hit the top*/
		if (enemySnake.getY()  <= 0)
		{
			enemysnakeMoveY=(int) (Math.random( )*enemySnakeRand);
			enemySnake.move(enemysnakeMoveX,enemysnakeMoveY);
		}
		
		/**Checks the snake to keep it in the applet*/
		if (enemySnake.getX()  <= 0)
		{	
			enemysnakeMoveX=(int) (Math.random( )*enemySnakeRand);
			enemySnake.move(enemysnakeMoveX, enemysnakeMoveY);
		}
		
		/**Checks the snake to keep it in the applet*/
		if (enemySnake.getX() + enemySnake.getHeight() >= WINDOW_X)
		{
			enemysnakeMoveX=(int) (Math.random( )*-enemySnakeRand);
			enemySnake.move(enemysnakeMoveX, enemysnakeMoveY);
		}
		
	}

}
