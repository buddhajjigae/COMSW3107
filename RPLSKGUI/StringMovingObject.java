import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class StringMovingObject implements MovingObject {
	private ThrowTypes throwName;
	private ThrowColors throwColor;
	private double throwX;		// upper 
	private double throwY;
	private double rectWidth;
	private double rectHeight;
	//private static Rectangle2D throwRectangle;
	private int DIMX = 1000;
	private int DIMY = 1000;
	private double velocityX;
	private double velocityY;

	public void update() {	
		
		throwX += velocityX;
		throwY += -velocityY; 

		//throwRectangle.setRect(throwX, throwY, throwRectangle.getWidth(), throwRectangle.getHeight());	
	}

	public void paintComponent(Graphics2D g2d) {
		Font throwFont = new Font("Helvetica", Font.BOLD, 28);
		FontRenderContext throwContext = g2d.getFontRenderContext();
		Rectangle2D throwRectangle = throwFont.getStringBounds(throwName.toString(), throwContext);
		rectWidth = throwRectangle.getWidth();
		rectHeight = throwRectangle.getHeight();
		// handles wraparound
		if (throwX < 0)
			throwX = DIMX; // this is getWidth() from Component
		if (throwY < 0)
			throwY = DIMY; // this is getWidth() from Component
		if (throwX > DIMX)
			throwX = 0; // this is getWidth() from Component
		if (throwY > DIMY)
			throwY = 0;

		//g2d.setColor(Color.RED);
		g2d.setFont(throwFont);
		g2d.setColor(throwColor.getColor());
		//g2d.drawString(throwName.toString(), (int) (throwX - throwRectangle.getWidth()), (int) (throwY - throwRectangle.getHeight())); // centralize
		g2d.drawString(throwName.toString(), (int) throwX, (int) throwY);
		//throwRectangle.setRect(thrxowX, throwY, throwRectangle.getWidth(), throwRectangle.getHeight());	
		//System.out.println( this.throwName +" : "+ throwRectangle + ", x=" + throwX+ ", y="+throwY );
	}

	public double getX() {
		return throwX;
	}
	
	public double getY() {
		return throwY;
	}
	
	public double getHeight() {
		return rectHeight;
	}
	
	public double getWidth() {
		return rectWidth;
	}
	
	public Rectangle2D getRectangle() {
		Rectangle2D throwRectangle = new Rectangle2D.Float();
		throwRectangle.setRect(throwX, throwY, rectWidth, rectHeight);
		return throwRectangle;
	}
	
	public ThrowTypes getName() {
		return throwName;
	}

	// BUILDER
	public static class Builder {
		private ThrowTypes throwName = ThrowTypes.SPOCK;
		private ThrowColors throwColor = ThrowColors.BLACK;
		private double throwX = 500;
		private double throwY = 0;		
		private double velocityX = 1;
		private double velocityY = 1;

		public Builder throwName(ThrowTypes name) {
			this.throwName = name;
			return this;
		}
		
		public Builder throwColor(ThrowColors color) {
			this.throwColor = color;
			return this;
		}

		public Builder throwX(double x) {
			this.throwX = x;
			return this;
		}

		public Builder throwY(double y) {
			this.throwY = y;
			return this;
		}
		
		public Builder velocityX(double velocity) {
			this.velocityX = velocity;
			return this;
		}
		
		public Builder velocityY(double velocity) {
			this.velocityY = velocity;
			return this;
		}
		
		public StringMovingObject build() {
			return new StringMovingObject(this);
		}
	}

	private StringMovingObject(Builder builder) {
			throwName = builder.throwName;
			throwColor = builder.throwColor;
			throwX = builder.throwX;
			throwY = builder.throwY;
			velocityX = builder.velocityX;
			velocityY = builder.velocityY;
		}
}
