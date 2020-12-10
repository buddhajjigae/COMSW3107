import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public interface MovingObject {
	void update();
	void paintComponent(Graphics g);
	int getX();
	int getY();
	double getHeight();
	double getWidth();
	Rectangle2D getRectangle();
}
