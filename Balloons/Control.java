
/**
 * The Control class is used to create the necessary gui objects to display the
 * MovingObjects (balloons) and functions to affect the MovingObject objects in the gui. 
 * It will also generate a random number of Balloons that will be displayed in the gui. 
 * It will also create a JSlider to allow the user to control the speed at which 
 * the MovingObject objects move on the x and y axis' and define a Timer that 
 * calls translate to change the x and y axis' of the MovingObject objects at a set 
 * interval in milliseconds. The start() function will run the Timer. 
 * 
 * @author Alex Yu ajy2116 based on John Kender's alteration of Cay Horstmann's code.
 * (Horstmann's Timer and Jframe logic used as guide) 
 *
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.Timer;

public class Control {
	private static JFrame frame;
	private static JSlider speedSlider;
	private static JLabel label;
	private static MyIcon icon;
	private static Timer timer;
	private static MassMovingObjects mass;
	private int speed;
	private static final double SIZE = 80;
	private static final int ICON_W = 900;
	private static final int ICON_H = 900;
	private static final int DELAY = 100;
	private static final int MAX_BALLOONS = 4;
	private static final int MIN_BALLOONS = 1;
	private static final int MAX_SPEED = 5;
	private static final int MIN_SPEED = -5;
	private static final int DEFAULT_SPEED = 1;
	private static final int MAX_BALLOON_GROUPS = 2;

	/**
	 * Class constructor that creates the JFrame, JLabel, and Icon along with the a
	 * MassMovingObjects object that will contain the objects that will be displayed
	 * in the gui. It also creates a JSlider that will allow the user to control the
	 * speed of the balloons on the y axis (negative or positive). It will also call
	 * generateMassBalloons to create the balloon objects to be displayed and
	 * createTimer to create the timer function that will move the MovingObject
	 * objects along the x and y axis'.
	 */
	public Control() {
		frame = new JFrame();
		mass = new MassMovingObjects();
		generateMassBalloons();
		icon = new MyIcon(mass, ICON_W, ICON_H);
		label = new JLabel(icon);
		speed = DEFAULT_SPEED;
		speedSlider = new JSlider(JSlider.VERTICAL, MIN_SPEED, MAX_SPEED, speed);
		speedSlider.setMajorTickSpacing(5);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				JSlider source = (JSlider) event.getSource();
				speed = source.getValue();
			}
		});
		frame.add(speedSlider);
		frame.add(label);
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		createTimer();
	}

	/**
	 * This method creates a Timer which will be called every DELAY (milliseconds)
	 * to call translate on the MovingObject. It will then repaint the MovingObject.
	 */
	private void createTimer() {
		timer = new Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mass.translate(0.0, -speed);
				label.repaint();
			}
		});
	}

	/**
	 * This method generates a random number of Balloon objects for a mass
	 * ascension. It does so by creating mass containers through MassMovingObjects
	 * and then adding Balloon objects that are wrapped with WindWrapper objects to
	 * those containers. The mass balloon containers are then wrapped with
	 * WindWrapper objects before being added to the over arching MassMovingObjects
	 * container. 
	 */
	private void generateMassBalloons() {
		for (int groupCount = 0; groupCount < MAX_BALLOON_GROUPS; groupCount++) {
			MassMovingObjects inner_mass = new MassMovingObjects();
			int balloonNum = (int) (Math.random() * (MAX_BALLOONS - MIN_BALLOONS) + MIN_BALLOONS);
			for (int number = 0; number < balloonNum; number++) {
				double randomX = Math.random() * 0.8 * ICON_W + 0.1 * ICON_W;
				double randomY = Math.random() * ICON_H * 0.3 + ICON_H * 0.7;
				double sizeRatio = Math.random() * (2 - 0.5) + .5;
				inner_mass.add(new WindWrapper(new Balloon(randomX, randomY, SIZE * sizeRatio)));
			}
			mass.add(new WindWrapper(inner_mass));
		}
	}

	/**
	 * This method starts the timer.
	 */
	public void start() {
		timer.start();
	}
}
