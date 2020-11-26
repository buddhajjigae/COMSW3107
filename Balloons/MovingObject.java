import java.awt.*;

/** *  * @author jrk based on cay horstmann * */

public interface MovingObject {
	void draw(Graphics2D g2D);
	void translate(int xChange, int yChange, double randomDrift);
}