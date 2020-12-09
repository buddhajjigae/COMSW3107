import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * @author jrk . based on cay horstmann modified from
 *         https://docs.oracle.com/javase/tutorial/uiswing/painting/step3.html
 * 
 *         fakes applet by 1) incorporating init() and paint() into main(), like
 *         Java Web Start 2) not using the functionality of start(), stop(), or
 *         destroy() 3) hardcoding JFrame size, instead of in html 4) hardcoding
 *         "throw" specifications in separate class
 */

public class Desktop2 {
	
	ArrayList<Throwable> throwList = new ArrayList<Throwable>();
	
	
	// Java 8 independent thread idiom, from tutorial
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	// the main() method for the graphics thread, from tutorial
	private static void createAndShowGUI() {
		// from tutorial: idiom to check on Event Dispatch Thread (EDT)
		System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
		// usual frame idioms
		JFrame myFrame = new JFrame("Desktop version");
		DesktopPanel myPanel = new DesktopPanel();
		//myFrame.add(myPanel);
		StringMovingObject obj = new StringMovingObject.Builder().throwName("Scissors").throwX(500).throwY(0).build();
		StringMovingObject obj2 = new StringMovingObject("Rock", 500, 0);
		myFrame.add(obj2);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
		// usual timer idioms
		Timer myTimer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// myPanel knows enough to do the job
				obj2.update(); // send signal to JPanel
				obj2.repaint();
			}
		});
		myTimer.start();
	}

}

// inner class, trying to be like applet, with all code in one file
class DesktopPanel extends JPanel {
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	protected void update() {
		throw1X--;
		
		throw2Y--;
	}

	protected void paintComponent(Graphics g) {
		// clear previous screen
		super.paintComponent(g);
		// need Graphics2D to get font render context
		Graphics2D g2d = (Graphics2D) g;
		// usual font idioms
		Font throwFont = new Font("Courier", Font.BOLD, 20);
		FontRenderContext throwContext = g2d.getFontRenderContext();
		// individual objects; in general, should be in an aggregate
		throw1Rectangle = throwFont.getStringBounds(throw1Name, throwContext);
		throw2Rectangle = throwFont.getStringBounds(throw2Name, throwContext);
		// handle wraparound for throw1
		if (throw1X + throw1Rectangle.getWidth() < 0)
			throw1X = getWidth(); // this is getWidth() from Component
		throw1Y = (int) -throw1Rectangle.getY();
		g2d.drawString(throw1Name, throw1X, throw1Y);
		// handle wraparound for throw2
		if (throw2Y + throw2Rectangle.getHeight() < 0)
			throw2Y = getHeight(); // this is getWidth() from Component
		throw2X = (int) -throw2Rectangle.getX();
		g2d.drawString(throw2Name, throw2X, throw2Y);
		// test for collision
		if (Math.abs(throw1X - throw2X) < 25 && Math.abs(throw1Y - throw2Y) < 25) {
			g2d.setColor(Color.RED);
			g2d.drawString(bang, 200, 200);
		}
	}

	private String bang = "BANG!";
	// these should all be created by a Builder
	private String throw1Name = "Rock";
	private static Rectangle2D throw1Rectangle;
	private static int throw1X = 500;
	private static int throw1Y;
	private String throw2Name = "Paper";
	private static Rectangle2D throw2Rectangle;
	private static int throw2X;
	private static int throw2Y = 500;
}
