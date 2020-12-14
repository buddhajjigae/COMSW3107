import java.awt.Color;

public enum ThrowColors {
	BLACK(Color.BLACK), MAGENTA(Color.BLUE), ORANGE(Color.CYAN), CYAN(Color.MAGENTA), GREEN(Color.GREEN);
	
	private final Color throwColor;
	
	ThrowColors(Color color) {
		throwColor = color;
	}
	
	Color getColor() {
		return throwColor;
	}
}
