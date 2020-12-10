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
	
	static ArrayList<Throwable> throwList = new ArrayList<Throwable>();
	
	
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
		StringMovingObject obj2 = new StringMovingObject("Rock", 500, 0);
		StringMovingObject obj = new StringMovingObject.Builder().throwName("Scissors").throwX(500).throwY(0).build();
		//DesktopPanel myPanel = new DesktopPanel(obj2);
		generateThrowables();
		DesktopPanel myPanel = new DesktopPanel(throwList);
		//myFrame.add(myPanel);


		myFrame.add(myPanel);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
		// usual timer idioms
		Timer myTimer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// myPanel knows enough to do the job
				myPanel.update(); // send signal to JPanel
				myPanel.collision(obj);
				myPanel.repaint();
			}
		});
		myTimer.start();
	}
	
	private static void generateThrowables() {
		throwList.add(new Throwable(new StringMovingObject("Rock", 400, 100)));
		throwList.add(new Throwable(new StringMovingObject("Scissor", 400, 100)));
	}

}

// inner class, trying to be like applet, with all code in one file
class DesktopPanel extends JPanel {
	private MovingObject obj;
	private ArrayList<Throwable> objList;

	
	public DesktopPanel(MovingObject obj) {
		this.obj = obj;
	}
	
	public DesktopPanel(ArrayList<Throwable> objList) {
		this.objList = objList;
	}
	
	
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	protected void update() {
		//obj.update();
		for(MovingObject obj : objList) {
			obj.update();
		}
	}
	
	protected boolean collision(MovingObject obj) {
		for(int i = 0; i < objList.size(); i++) {
			for(int j = i + 1; j < objList.size(); j++) {
				if(objList.get(i).collision(objList.get(j))) {
					System.out.println("HERE");
					return true;
				}
			}
		}
		return false;
	}

	protected void paintComponent(Graphics g) {
		// clear previous screen
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for(MovingObject obj : objList) {
			obj.paintComponent(g);
		}
		//obj.paintComponent(g);
	}
}
