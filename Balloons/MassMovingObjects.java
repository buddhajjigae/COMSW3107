
/**
 * The MassMovingObjects class is used to store MovingObjects into an ArrayList.
 * It can contain any object that is a MovingObject including MassMovingObjects.
 * 
 * @author Alex Yu ajy2116 based on John Kender's alteration of Cay Horstmann's code.
 * (Horstmann's translate and draw logic used as guide) 
 *
 */

import java.awt.Graphics2D;
import java.util.ArrayList;

public class MassMovingObjects implements MovingObject {
	private ArrayList<MovingObject> objectArray;

	/**
	 * Class constructor used to initialize the ArrayList of MovingObject's.
	 */
	public MassMovingObjects() {
		objectArray = new ArrayList<MovingObject>();
	}

	/**
	 * This draw method will call draw on each of the MovingObject's in objectArray.
	 * 
	 * @param graphics2D the graphics2D that the MovingObject's will be drawn onto
	 */
	public void draw(Graphics2D graphics2D) {
		for (MovingObject object : objectArray) {
			object.draw(graphics2D);
		}
	}

	/**
	 * This draw method will call translate from each of the MovingObject's in
	 * objectArray.
	 * 
	 * @param xChange the amount of change for the x coordinate of the MovingObject
	 * @param yChange the amount of change for the y coordinate of the MovingObject
	 */
	public void translate(double xChange, double yChange) {
		for (MovingObject object : objectArray) {
			object.translate(xChange, yChange);
		}
	}

	/**
	 * This draw method will add a MovingObject into the objectArray.
	 * 
	 * @param object a MovingObject
	 */
	public void add(MovingObject object) {
		objectArray.add(object);
	}
}
