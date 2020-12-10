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


public class StringMovingObject implements MovingObject {


	private String throwName;
	private int throwX;
	private int throwY;
	private static Rectangle2D throwRectangle;
	private int DIMX = 500;
	private int DIMY = 500;
	private int velocityX = 1;
	private int velocityY = 1;

	
	public StringMovingObject(String name, int x, int y) {
		this.throwName = name;
		this.throwX = x;
		this.throwY = y;
	}
	
	public void update() {	
		throwX += -velocityX;
		throwY += -velocityY;
	}

	public void paintComponent(Graphics g) {
		System.out.println(throwX + "," + throwY);
		// clear previous screen
		// need Graphics2D to get font render context
		Graphics2D g2d = (Graphics2D) g;
		// usual font idioms
		Font throwFont = new Font("Courier", Font.BOLD, 20);
		FontRenderContext throwContext = g2d.getFontRenderContext();
		// individual objects; in general, should be in an aggregate
		throwRectangle = throwFont.getStringBounds(throwName, throwContext);
		// handles wraparound
		if (throwX + throwRectangle.getWidth() < 0)
			throwX = DIMX; // this is getWidth() from Component
		if (throwY + throwRectangle.getWidth() < 0)
			throwY = DIMY; // this is getWidth() from Component
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString(throwName, throwX, throwY);
	}

	
	public int getX() {
		return throwX;
	}
	
	public int getY() {
		return throwY;
	}
	
	public double getHeight() {
		return throwRectangle.getHeight();
	}
	
	public double getWidth() {
		return throwRectangle.getWidth();
	}
	
	public Rectangle2D getRectangle() {
		return throwRectangle;
	}
		
	public static class Builder {
		private String throwName = "Rock";
		private int throwX = 500;
		private int throwY = 0;		
		private int velocityX = 1;
		private int velocityY = 1;

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
		
		public Builder velocityX(int velocity) {
			this.velocityX = velocity;
			return this;
		}
		
		public Builder velocityY(int velocity) {
			this.velocityY = velocity;
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
			this.velocityX = builder.velocityX;
			this.velocityY = builder.velocityY;
		}
}
