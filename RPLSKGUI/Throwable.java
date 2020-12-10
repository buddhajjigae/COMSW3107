import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class Throwable implements MovingObject {
	MovingObject object;
	
	public Throwable(MovingObject obj) {
		this.object = obj;
	}
	
	public void update() {
		object.update();
	}

	public void paintComponent(Graphics g) {	
		object.paintComponent(g);
	}

	public boolean collision(MovingObject obj) {
		double height = obj.getHeight();
		double width = obj.getWidth();
		double x = obj.getX() - .5 * height;
		double y = obj.getY() - .5 * width;
		Rectangle2D rect = object.getRectangle();
		if(rect.intersects(x, y, height, width)) {
			System.out.println("====================");
			System.out.println("WORKED");
			System.out.println("====================");
			return true;
		}
		return false;
	}

	public int getX() {
		return object.getX();
	}

	public int getY() {
		return object.getY();
	}
	
	public double getHeight() {
		return object.getHeight();
	}
	
	public double getWidth() {
		return object.getWidth();
	}
	
	public Rectangle2D getRectangle() {
		return object.getRectangle();
	}


}
