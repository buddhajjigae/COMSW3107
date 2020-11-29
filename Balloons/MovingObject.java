
/**
 * The MovingObject interface used for draw() and translate() methods. 
 * 
 * @author Alex Yu ajy2116 using John Kender's alteration of Cay Horstmann's code.
 * 
 *
 */

import java.awt.*;

public interface MovingObject {
	void draw(Graphics2D graphics2D);

	void translate(double d, double yChange);
}
