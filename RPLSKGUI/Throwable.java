import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Throwable implements MovingObject {
	private MovingObject object;


	public Throwable(MovingObject obj) {
		this.object = obj;
	}

	public void update() {
		object.update();
		
	}

	public void paintComponent(Graphics2D g2d) {
		object.paintComponent(g2d);
	}

	public boolean collision(MovingObject obj) {
		Rectangle2D rect = this.getRectangle();
	
		//rect.setRect( rect.getX() - 0.5*rect.getWidth(), rect.getY() - 0.5*rect.getHeight(), rect.getWidth(), rect.getHeight() );
		if (rect.intersects(obj.getX(), obj.getY(), obj.getWidth() - 10, obj.getHeight() - 10)) {
			return true;
		}
		return false;
	}

	public double getX() {
		return object.getX();
	}

	public double getY() {
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
	
	public ThrowTypes getName() {
		return object.getName();
	}
}
