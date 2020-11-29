
/**
 * The MyIcon class will create an Icon based on the given width and height and
 * paint the objects in objects in the Icon. 
 * 
 * @author Alex Yu ajy2116 based on John Kender's alteration of Cay Horstmann's code.
 * (Horstmann's logic used as guide) 
 *
 */

import java.awt.*;
import javax.swing.*;

public class MyIcon implements Icon {
	private int iconWidth;
	private int iconHeight;
	private MovingObject object;

	/**
	 * Class constructor used to set the icon width, height, and object.
	 * 
	 * @param object the MovingObject of the icon
	 * @param w      the width of the icon
	 * @param h      the height of the icon
	 */
	public MyIcon(MovingObject object, int w, int h) {
		this.object = object;
		this.iconWidth = w;
		this.iconHeight = h;
	}

	/**
	 * Helper method to get icon width.
	 * 
	 * @return returns the icon width
	 */
	public int getIconWidth() {
		return iconWidth;
	}

	/**
	 * Helper method to get icon height.
	 * 
	 * @return returns the icon height
	 */
	public int getIconHeight() {
		return iconHeight;
	}

	/**
	 * Method to paint the MovingObject.
	 * 
	 * @param component the component that will be used if the icon has no image
	 *                  observer
	 * @param graphic   the context of the graphic
	 * @param x         the icon's top-left corner x coordinate
	 * @param y         the icon's top-left corner y coordinate
	 */
	public void paintIcon(Component component, Graphics graphic, int x, int y) {
		Graphics2D graphics2D = (Graphics2D) graphic;
		object.draw(graphics2D);
	}
}
