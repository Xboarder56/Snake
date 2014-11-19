/** 
 * Snake.java
 * @author Garrett J. Beasley
 * 11/16/2014
 * Snake class for making snake GCompound
 */

 /** Imports for the project */
 import java.awt.Color;
 import acm.graphics.GCompound;
 import acm.graphics.*;

public class Snake extends GCompound
{
	/**Init of variables*/
	private int startX, startY, snakeBodyDiam; 
	private Color ballColor;
	public static final int locationX =0, locationY = 0;
	
	public Snake(Color color, int bodySize)
	{
		/**helper classes changes from input to another variable accessible by the program*/
		startX=locationX;
		startY=locationY;
		ballColor = color;
		snakeBodyDiam = bodySize;
		
		/**Calls the ball method createBall*/
		createSnake();
	}
	
	/**createball Method where it makes the ball for the gcompound*/
	public void createSnake()
	{
		/**Creates all the govals used to make the ball based off the set location startX/startY/ballDiam*/
		GOval snake = new GOval(startX,startY, snakeBodyDiam, snakeBodyDiam);
		
		/**Sets the color to the color that is passed into the program*/
		snake.setFillColor(ballColor);
		
		/**Sets the objects to filled true*/
		snake.setFilled(true);
		
		/**Adds the objects to the gcompound*/
		add(snake);
	}
}
