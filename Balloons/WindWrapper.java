
/**
 * The WindWrapper class is a MovingObject that contains a generateWind function which
 * is then used in translate to send the new xChange + wind to a MovingObject. In this program,
 * it will send the xChange + wind to a Balloon objects translate since it will hold
 * a Balloon object in object. Wind, in this case, is the amount that will be added to the
 * x coordinate in order to make the MovingObject move left or right. 
 * 
 * @author Alex Yu ajy2116 based on John Kender's alteration of Cay Horstmann's code.
 * (Horstmann's translate and draw logic used as guide) 
 *
 */

import java.awt.Graphics2D;

public class WindWrapper implements MovingObject {
	private MovingObject object;
	private double wind;

	/**
	 * Class constructor to set this object to the given object.
	 * 
	 * @param object a MovingObject
	 */
	public WindWrapper(MovingObject object) {
		this.object = object;
	}

	/**
	 * This draw method will call draw on the MovingObject that object contains.
	 * 
	 * @param graphics2D the graphics2D that the MovingObject will be drawn onto
	 */
	public void draw(Graphics2D graphics2D) {
		object.draw(graphics2D);
	}

	/**
	 * This draw method will call translate on the MovingObject that object
	 * contains.
	 * 
	 * @param xChange the amount of change for the x coordinate of the MovingObject
	 * @param yChange the amount of change for the y coordinate of the MovingObject
	 */
	public void translate(double xChange, double yChange) {
		generatewind();
		object.translate(xChange + wind, yChange);
	}

	/**
	 * This draw method will set this object to the given MovingObject.
	 * 
	 * @param object a MovingObject
	 */
	public void add(MovingObject object) {
		this.object = object;
	}

	/**
	 * This method generates a random wind value between in a given range to then be
	 * added to xChange in translate. Doing so will make the MovingObject move left
	 * or right randomly depending on the value of the wind generated. If statement
	 * checks are made to ensure that wind does not get too high or low.
	 */
	private void generatewind() {
		wind += Math.random() * 0.25 - 0.125;
		if (wind < -8) {
			wind = -8;
		} else if (wind > 8) {
			wind = 8;
		}
	}
}
