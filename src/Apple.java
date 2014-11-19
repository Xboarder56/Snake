/** 
* Apple.java
 * @author Garrett J. Beasley
 * 11/16/2014
 * Apple class for making Apple GCompound
 */

 /** Imports for the project */
 import java.awt.Color;
 import acm.graphics.GCompound;
 import acm.graphics.*;

public class Apple extends GCompound
{
	/**Init of variables*/
	private int startX, startY, appleDiam; 
	private Color appleColor;
	public static final int locationX =0, locationY = 0;
	
	public Apple(Color color, int bodySize)
	{
		/**helper classes changes from input to another variable accessible by the program*/
		startX=locationX;
		startY=locationY;
		appleColor = color;
		appleDiam = bodySize;
		
		/**Calls the ball method createBall*/
		createApple();
	}
	
	/**createball Method where it makes the ball for the gcompound*/
	public void createApple()
	{
		/**Creates all the govals used to make the ball based off the set location startX/startY/ballDiam*/
		GOval apple = new GOval(startX,startY, appleDiam, appleDiam);
		
		/**Sets the color to the color that is passed into the program*/
		apple.setFillColor(appleColor);
		
		/**Sets the objects to filled true*/
		apple.setFilled(true);
		
		/**Adds the objects to the gcompound*/
		add(apple);
	}
}
