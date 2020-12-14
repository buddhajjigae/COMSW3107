import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public interface MovingObject {
	void update();
	void paintComponent(Graphics2D g2d);
	double getX();
	double getY();
	double getHeight();
	double getWidth();
	Rectangle2D getRectangle();
	ThrowTypes getName();
}
