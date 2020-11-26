import java.awt.*;
import java.awt.geom.*;

/** * * @author jrk based on cay horstmann * */

public class Balloon implements MovingObject {
	private int x;
	private int y;
	private int unit;
	private final double cableAngle = Math.PI / 3;
	private final double angle2 = Math.PI / 8;
	private final double BASKETDISTANCE = 2.0;
	private final double BASKETWIDTH = .4;
	private final double BASKETHEIGHT = .25;
	GeneralPath path;

	public Balloon(int x, int y, int unit) {
		this.x = x;
		this.y = y;
		this.unit = unit;
	}

	public void translate(int xChange, int yChange, double randomDrift) {
		if (xChange == 0) {
			x += randomDrift;
			y += yChange;
			path.moveTo(x, y);
		} else {
			x += xChange;
			y += yChange;
			path.moveTo(x, y);
		}
	}
	
	public void draw(Graphics2D g2D) {
		// envelope
		int envelopeRadius = unit / 2;
		double balloonHeight = unit * BASKETHEIGHT + envelopeRadius + envelopeRadius * BASKETDISTANCE;
		int envelopeXUp = x;
		int envelopeYUp = y - (int) balloonHeight;
		//int envelopeYBot = envelopeYUp + unit;
		Ellipse2D.Double envelope = new Ellipse2D.Double(envelopeXUp, envelopeYUp, envelopeRadius * 2,
				envelopeRadius * 2);

		// cables
		double cableLXTop = envelopeXUp + envelopeRadius + envelopeRadius * Math.cos(Math.PI - cableAngle);
		double cableLYTop = envelopeYUp + envelopeRadius + envelopeRadius * Math.sin(Math.PI - cableAngle);
		double cableRXTop = envelopeXUp + envelopeRadius + envelopeRadius * Math.cos(cableAngle);
		double cableRYTop = envelopeYUp + envelopeRadius + envelopeRadius * Math.sin(cableAngle);
		double cableXLBot = envelopeXUp + envelopeRadius - BASKETWIDTH * 10;
		double cableXYBot = envelopeXUp + envelopeRadius + BASKETWIDTH * 10;
		double cableYBot = envelopeYUp + envelopeRadius + envelopeRadius * BASKETDISTANCE;
		Line2D.Double cableL = new Line2D.Double(cableLXTop, cableLYTop, cableXLBot, cableYBot);
		Line2D.Double cableR = new Line2D.Double(cableRXTop, cableRYTop, cableXYBot, cableYBot);
		
		// basket
		double basketXTop = cableLXTop + envelopeRadius + envelopeRadius * Math.cos(Math.PI - angle2);
		double basketYTop = cableYBot;
		Rectangle2D.Double basket = new Rectangle2D.Double(basketXTop, basketYTop, unit * BASKETWIDTH,
				unit * BASKETHEIGHT);
		
		path = new GeneralPath();
		path.append(envelope, false);
		path.append(basket, false);
		path.append(cableL, false);
		path.append(cableR, false);

		g2D.fill(path);
		g2D.draw(path);
	}
}