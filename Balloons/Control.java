
/**
 * The Control class is used to create the necessary gui objects to display the
 * balloons and functions to affect the balloon objects in the gui. It will also
 * create a JSlider to control the speed at which the balloon objects move on the 
 * x and y axis'. 
 * 
 * @author Alex Yu ajy2116 based on John Kender's alteration of Cay Horstmann's code.
 * (Horstmann's Timer and Jframe logic used as guide) 
 *
 */

import java.util.ArrayList;
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
	private static ArrayList<MovingObject> balloons;
	private int speed;
	private double randomDrift;
	private static final int SIZE = 60;
	private static final int ICON_W = 900;
	private static final int ICON_H = 900;
	private static final int DELAY = 100;
	private static final int MAX_BALLOONS = 7;
	private static final int MIN_BALLOONS = 3;
	private static final int MAX_SPEED = 5;
	private static final int MIN_SPEED = -5;

	/**
	 * Class constructor that creates the JFrame along with the objects that will be
	 * displayed in the frame. It also creates a JSlider that will allow the user to
	 * control the speed of the balloons on the y axis (negative or positive). It
	 * will also call generateMassBalloons to create the balloon objects to be
	 * displayed and createTimer to create the timer function that will move the
	 * balloons along the x and y axis'.
	 */
	public Control() {
		frame = new JFrame();
		balloons = new ArrayList<MovingObject>();
		icon = new MyIcon(balloons, ICON_W, ICON_H);
		label = new JLabel(icon);
		speed = 1;

		speedSlider = new JSlider(JSlider.VERTICAL, MIN_SPEED, MAX_SPEED, speed);
		speedSlider.setMajorTickSpacing(5);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);

		/**
		 * Creates a ChangeListener for JSlider that changes speed based on user input
		 * (moving the slider).
		 */
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

		generateMassBalloons();
		createTimer();
	}

	/**
	 * This method creates a Timer which will be called every DELAY (milliseconds)
	 * to change the speed of the balloon objects (x and y coordinates). It will
	 * then repaint the balloons through the JLabel object.
	 */
	private void createTimer() {
		timer = new Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				randomDrift = generateDrift();
				for (MovingObject obj : balloons) {
					obj.translate(0, -speed, randomDrift);
				}
				label.repaint();
			}
		});
	}

	/**
	 * This method generates a random number of balloons using the Balloon class. It
	 * will create between the predefined max and min number of balloons. Each
	 * balloon will be generated at a random x and y coordinate within predefined
	 * boundary. It will then add each generated balloon to the balloons array list.
	 */
	private void generateMassBalloons() {
		int balloonNumber = (int) (Math.random() * (MAX_BALLOONS - MIN_BALLOONS) + MIN_BALLOONS);
		System.out.println(balloonNumber);
		for (int number = 0; number <= balloonNumber; number++) {
			double randomX = Math.random() * 0.8 * ICON_W + 0.1 * ICON_W;
			double randomY = Math.random() * ICON_H * 0.3 + ICON_H * 0.7;
			balloons.add(new Balloon((int) randomX, (int) randomY, SIZE));
		}
	}

	/**
	 * This method generates a random drift value that will be used to drift the
	 * balloon(s) left to right on the x-axis and sets the member variable
	 * randomDrift to the generated value. It will prevent excessive drift by
	 * restricting the randomDrift value between 10 and -10.
	 * 
	 * @return returns the new randomDrift value
	 */
	private double generateDrift() {
		randomDrift += Math.random() * 0.2 - 0.1;
		if (randomDrift < -10) {
			randomDrift = -10;
		} else if (randomDrift > 10) {
			randomDrift = 10;
		}
		return randomDrift;
	}

	/**
	 * This method starts the timer which will change the x and y coordinates of
	 * each balloon that has been generated.
	 */
	public void start() {
		timer.start();
	}
}
