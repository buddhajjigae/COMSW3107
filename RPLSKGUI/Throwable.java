import java.awt.Graphics;
import java.awt.Point;

public class Throwable implements MovingObject {
	MovingObject object;
	
	public Throwable(MovingObject obj) {
		this.object = obj;
	}
	
	public void paintComponent(Graphics g) {
		object.paintComponent(g);
	}

	public Point getLocation() {
		return null;
	}
	
	public void update() {
		object.update();
	}
}
