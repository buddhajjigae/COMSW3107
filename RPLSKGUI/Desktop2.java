import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
		// DesktopPanel myPanel = new DesktopPanel(obj2);

		DesktopPanel myPanel = new DesktopPanel();
		// myFrame.add(myPanel);
		// myPanel.generateThrowables();
		myFrame.add(myPanel);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
		// usual timer idioms
		Timer myTimer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// myPanel knows enough to do the job
				myPanel.update(); // send signal to JPanel
				myPanel.repaint();
				myPanel.collision();

			}
		});
		myTimer.start();
	}
}

// inner class, trying to be like applet, with all code in one file
class DesktopPanel extends JPanel {
	private ArrayList<Throwable> objList;
	ArrayList<String> throwPairs;
	private final int MAX_DIM_X = 1000;
	private final int MAX_DIM_Y = 1000;
	String outcome = "";

	public DesktopPanel() {
		objList = new ArrayList<Throwable>();
		throwPairs = new ArrayList<String>();
		generateThrowables();
	}

	public Dimension getPreferredSize() {
		return new Dimension(MAX_DIM_X, MAX_DIM_Y);
	}

	public void collision() {
		ArrayList<Throwable> removeList = new ArrayList<Throwable>();
		ArrayList<Throwable> addList = new ArrayList<Throwable>();

		for (int i = 0; i < objList.size(); i++) {
			for (int j = i + 1; j < objList.size(); j++) {
				if (objList.get(i).collision(objList.get(j))) {
					outcome = "";
					if (Handle.outcomes(objList.get(i), objList.get(j)) == Outcomes.WIN) {
						removeList.add(objList.get(j));
						throwPairs.add(objList.get(i).getName().toString());
						throwPairs.add(Outcomes.WIN.getOutcome());
						throwPairs.add(objList.get(j).getName().toString());
					} else if (Handle.outcomes(objList.get(i), objList.get(j)) == Outcomes.LOSS) {
						removeList.add(objList.get(i));
						throwPairs.add(objList.get(j).getName().toString());
						throwPairs.add(Outcomes.WIN.getOutcome());
						throwPairs.add(objList.get(i).getName().toString());
					} else {
						removeList.add(objList.get(i));
						removeList.add(objList.get(j));
						addList.add(objList.get(i));
						throwPairs.add(objList.get(i).getName().toString());
						throwPairs.add(Outcomes.TIE.getOutcome());
						throwPairs.add(objList.get(j).getName().toString());
					}
				}
			}
		}
		removeThrows(removeList);
		addThrows(addList);
	}

	public void generateThrowables() {
		for (int i = 0; i < 15; i++) {
			ThrowTypes randomThrow = generateThrowType();
			ThrowColors randomColor = generateColor();
			double randomX = Math.random() * (MAX_DIM_X);
			double randomY = Math.random() * (MAX_DIM_Y);
			double randomVelocityX = generateVelocity();
			double randomVelocityY = generateVelocity();
			objList.add(new Throwable(new StringMovingObject.Builder().throwName(randomThrow).throwColor(randomColor)
					.throwX(randomX).throwY(randomY).velocityX(randomVelocityX).velocityY(randomVelocityY).build()));
		}
	}

	public void addThrows(ArrayList<Throwable> addList) {
		for (int i = 0; i < addList.size(); i++) {
			ThrowColors randomColor = generateColor();
			double randomVelocityX = generateVelocity();
			double randomVelocityY = generateVelocity();
			objList.add(new Throwable(new StringMovingObject.Builder().throwName(addList.get(i).getName())
					.throwColor(randomColor).throwX(addList.get(i).getX()).throwY(addList.get(i).getY())
					.velocityX(randomVelocityX).velocityY(randomVelocityY).build()));
		}
	}

	public void removeThrows(ArrayList<Throwable> removeList) {
		for (int i = 0; i < removeList.size(); i++) {
			objList.remove(removeList.get(i));
		}
	}

	private ThrowTypes generateThrowType() {
		Random random = new Random();
		ThrowTypes throwTypes[] = ThrowTypes.values();
		return throwTypes[random.nextInt(throwTypes.length)];
	}

	private ThrowColors generateColor() {
		ThrowColors throwColors[] = ThrowColors.values();
		Random random = new Random();
		return throwColors[random.nextInt(throwColors.length)];
	}

	private double generateVelocity() {
		return Math.random() * 8 - 4;
	}

	protected void update() {
		for (MovingObject obj : objList) {
			obj.update();
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		for (MovingObject obj : objList) {
			obj.paintComponent(g2d);
		}
			
		for (String s : throwPairs) {
			outcome += s + " ";
		}
		
		if (objList.size() == 1) {
			outcome = "";
			outcome = objList.get(0).getName().toString();
			objList.clear();
			outcome = outcome + " WINS! GAME OVER!";
		}
		
		Font throwFont = new Font("SansSerif", Font.BOLD, 30);
		
		throwPairs.clear();
		g2d.setColor(Color.RED);
		g2d.setFont(throwFont);
		g2d.drawString(outcome, (MAX_DIM_X / 2) - (MAX_DIM_X / 8), MAX_DIM_Y - 10);
	}
}
