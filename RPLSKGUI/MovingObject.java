import java.awt.Graphics;
import java.awt.Point;

public interface MovingObject {
	void update();
	void paintComponent(Graphics g);
	Point getLocation();
}