import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;


public class StringMovingObject extends JPanel implements MovingObject {

	private String throwName;
	private int throwX;
	private int throwY;
	private static Rectangle2D throwRectangle;
	
	public StringMovingObject(String name, int x, int y) {
		this.throwName = name;
		this.throwX = x;
		this.throwY = y;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	public int getWidth() {
		return (int) throwRectangle.getWidth();
	}
	
	public int getHeight() {
		return (int) throwRectangle.getHeight();
	}
	
	public void update() {	
		throwX--;
	}

	public void paintComponent(Graphics g) {
		// clear previous screen
		super.paintComponent(g);
		// need Graphics2D to get font render context
		Graphics2D g2d = (Graphics2D) g;
		// usual font idioms
		Font throwFont = new Font("Courier", Font.BOLD, 20);
		FontRenderContext throwContext = g2d.getFontRenderContext();
		// individual objects; in general, should be in an aggregate
		throwRectangle = throwFont.getStringBounds(throwName, throwContext);
		// handles wraparound
		if (throwX + throwRectangle.getWidth() < 0)
			throwX = getWidth(); // this is getWidth() from Component
		g2d.drawString(throwName, throwX, throwY);
	}

	public Point getLocation() {
		return null;
	}
	
	
	public static class Builder {

		private String throwName = "Rock";
		private int throwX = 500;
		private int throwY = 0;

		public Builder throwName(String name) {
			this.throwName = name;
			return this;
		}

		public Builder throwX(int x) {
			this.throwX = x;
			return this;
		}

		public Builder throwY(int y) {
			this.throwY = y;
			return this;
		}
		
		public StringMovingObject build() {
			return new StringMovingObject(this);
		}
	}

	private StringMovingObject(Builder builder) {
			this.throwName = builder.throwName;
			this.throwX = builder.throwX;
			this.throwY = builder.throwY;
		}
}