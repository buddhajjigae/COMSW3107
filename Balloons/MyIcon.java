import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/** * * @author jrk based on cay horstmann * */

public class MyIcon implements Icon {
	private int w;
	private int h;
	private ArrayList<MovingObject> objects;
	private MovingObject obj;
	
	public MyIcon(ArrayList<MovingObject> objects, int w, int h) {
		this.objects = objects;
		this.w = w;
		this.h = h;
	}

	public int getIconWidth() {
		return w;
	}

	public int getIconHeight() {
		return h;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2D = (Graphics2D) g;
		for(MovingObject obj : objects) {
			obj.draw(g2D);
		}
	}
}