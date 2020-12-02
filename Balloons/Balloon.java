
/**
 * The Balloon class defines a hot air balloon object as a MovingObject. It contains a translate 
 * function that changes the x and y position of a balloon. Draw is used to paint the balloon object
 * on the respective Icon. Any movement of a Balloon object will occur in it's translate() function. 
 * 
 * @author Alex Yu ajy2116 based on John Kender's alteration of Cay Horstmann's code.
 * (Horstmann's translate and draw logic used as guide) 
 *
 */

import java.awt.*;
import java.awt.geom.*;

public class Balloon implements MovingObject {
	private double x;
	private double y;
	private double size;
	private GeneralPath path;
	private final double CABLE_ANGLE = Math.PI / 3;
	private final double BASKET_ANGLE = Math.PI / 8;
	private final double BASKET_DISTANCE = 2.0;
	private final double BASKET_WIDTH = .4;
	private final double BASKET_HEIGHT = .25;

	/**
	 * Class constructor used to initialize the initial x and y positions of the
	 * balloon along with the balloon size.
	 * 
	 * @param x    the x coordinate of the balloon
	 * @param y    the y coordinate of the balloon
	 * @param size the size of the balloon
	 */
	public Balloon(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.size = size;

	}

	/**
	 * This method is used to change the x and y coordinates of the balloon by using
	 * the given xChange and yChange arguments to set a new x and y value.
	 * 
	 * @param xChange the amount that the balloon will move on the x-axis
	 * @param yChange the amount that the balloon will move on the y-axis
	 */
	public void translate(double xChange, double yChange) {
		x += xChange;
		y += yChange;
		path.moveTo(x, y);
	}

	/**
	 * Draw defines how the cables, envelope, and basket of the balloon will be
	 * drawn relative to each other at a given x and y coordinate and size. It will
	 * then append the three pieces onto a single GeneralPath.
	 * 
	 * @param graphics2D the graphics2D that the balloon will be drawn onto
	 */
	public void draw(Graphics2D graphics2D) {
		// Envelope
		double envelopeRadius = size / 2;
		double balloonHeight = size * BASKET_HEIGHT + envelopeRadius + envelopeRadius * BASKET_DISTANCE;
		double envelopeXUp = x;
		double envelopeYUp = y - balloonHeight;
		Ellipse2D.Double envelope = new Ellipse2D.Double(envelopeXUp, envelopeYUp, envelopeRadius * 2,
				envelopeRadius * 2);
		// Cables
		double cableLXTop = envelopeXUp + envelopeRadius + envelopeRadius * Math.cos(Math.PI - CABLE_ANGLE);
		double cableLYTop = envelopeYUp + envelopeRadius + envelopeRadius * Math.sin(Math.PI - CABLE_ANGLE);
		double cableRXTop = envelopeXUp + envelopeRadius + envelopeRadius * Math.cos(CABLE_ANGLE);
		double cableRYTop = envelopeYUp + envelopeRadius + envelopeRadius * Math.sin(CABLE_ANGLE);
		double cableXLBot = envelopeXUp + envelopeRadius - BASKET_WIDTH * size / 6.5;
		double cableXYBot = envelopeXUp + envelopeRadius + BASKET_WIDTH * size / 6.5;
		double cableYBot = envelopeYUp + envelopeRadius + envelopeRadius * BASKET_DISTANCE;
		Line2D.Double cableL = new Line2D.Double(cableLXTop, cableLYTop, cableXLBot, cableYBot);
		Line2D.Double cableR = new Line2D.Double(cableRXTop, cableRYTop, cableXYBot, cableYBot);
		// Basket
		double basketXTop = cableLXTop + envelopeRadius + envelopeRadius * Math.cos(Math.PI - BASKET_ANGLE);
		double basketYTop = cableYBot;
		Rectangle2D.Double basket = new Rectangle2D.Double(basketXTop, basketYTop, size * BASKET_WIDTH,
				size * BASKET_HEIGHT);
		path = new GeneralPath();
		path.append(envelope, false);
		path.append(basket, false);
		path.append(cableL, false);
		path.append(cableR, false);

		graphics2D.fill(path);
		graphics2D.draw(path);
	}
}
