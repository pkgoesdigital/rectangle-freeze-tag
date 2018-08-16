import edu.princeton.cs.introcs.*;
import java.util.Random;

public class MovingRectangleDriver {

	public static void main(String[] args) {
		/*
		 * Paula Klimas
		 * Moving Rectangle Project
		 * 13 March 2018
		 */
		//set up StdDraw Canvas
		StdDraw.setCanvasSize(700, 700);
		StdDraw.setScale(0,100);

		StdDraw.enableDoubleBuffering();

		//create 5 rectangle objects with array
		MovingRectangle [] rectangles = new MovingRectangle[5];

		//create random number generator object
		Random rng = new Random(); 

		//create randomly generated values for each rectangle object in MovingRectangle array
		//random x,y placement on canvas, random dimensions of each rectangle, random velocities in x & y directions, random click count
		for(int i = 0; i < rectangles.length; ++i) 
		{
			rectangles[i] = new MovingRectangle(
					rng.nextDouble()*100, //xCoord random
					rng.nextDouble()*100, //yCoord random
					rng.nextDouble()+rng.nextDouble()*15, //width random
					rng.nextDouble()+rng.nextDouble()*15, //height random
					rng.nextDouble()*0.5, //xv random
					rng.nextDouble()*0.5, //yv random
					rng.nextInt(3) + 1 //remaining clicks
					);
		}

		boolean clicked = false;

		//keeping track number of frozen rectangles
		int frozenRectangleCount = 0;

		while (true) 
		{
			//clear the canvas
			StdDraw.clear();
			for(int j = 0; j < rectangles.length; j++) 
			{
				//checking whether ismousePressed value for both x and y coordinates
				boolean goIn = StdDraw.isMousePressed() && rectangles[j].hasMouse(StdDraw.mouseX(), StdDraw.mouseY());
				//check to see if user is clicking mouse
				if (goIn && !clicked) 
				{
					int clicks = rectangles[j].getRemainingClicks();
					//check to see value of clicks, if clicks is 0 then rectangles freeze and turn red
					if(clicks == 1) 
					{
						rectangles[j].freeze();
						rectangles[j].isFrozen();
						//increment frozenRectangleCount to track # of frozen rectangles in game at all times
						++frozenRectangleCount;
						//decrement clicks value - otherwise clicks will always be 1
						clicks = 0;
					}
					else 
					{
						--clicks;
						rectangles[j].setRemainingClicks(clicks);
						clicked = false;
					}
					clicked = true;
				} 
				if (!StdDraw.isMousePressed()) 
				{
					//sets clicked to false for next iteration
					clicked = false;
				}
				//if rectangles collide, unfreeze rectangles (reinitiate animate), reset clicks, and redraw
				for (int i = j+1; i < rectangles.length; i++) 
				{
					if (rectangles[i].collidesWith(rectangles[j])) 
					{
						if(rectangles[i].isFrozen()) 
						{
							rectangles[i].unfreeze();
							rectangles[i].resetRemainingClicks();
							--frozenRectangleCount;
						}
						else if(rectangles[j].isFrozen())
						{
							rectangles[j].unfreeze();
							rectangles[j].resetRemainingClicks();
							--frozenRectangleCount;
						}
					}
				}
				//uncomment for prismacolor rectangles !
//								rectangles[j].randomColor();
				rectangles[j].animate();
				rectangles[j].draw();

				//printing out win message when all rectangles are frozen by checking if frozenRectangleCount value is equivalent to length of array
				if (frozenRectangleCount >= rectangles.length) 
				{
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledRectangle(50, 50, 10, 10);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.text(50, 50, "You Win!");
				}
			}
			//uncomment to show frozenRectangleCount on StdDraw canvas at all times
			//			StdDraw.setPenColor(StdDraw.BLACK);
			//			StdDraw.text(20, 10, frozenRectangleCount + "");
			//StdDraw.show mirrors backbuffer enabled from StdDraw.enableDoubleBuffering()
			StdDraw.show();
			StdDraw.pause(20);
		}

	}
}