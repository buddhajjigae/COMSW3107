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
	private static MyIcon icon ;
	private static Timer timer;
	private static ArrayList<MovingObject> objects;
	private int speed;
	private double randomDrift;
	private static final int SIZE = 60;
	private static final int ICON_W = 900;
	private static final int ICON_H = 450;
	private static final int DELAY = 100;

	public Control() {
		frame = new JFrame();
		objects = new ArrayList<MovingObject>();
		icon = new MyIcon(objects, ICON_W, ICON_H);
		label = new JLabel(icon);
		speed = 1;
		//double randomX = Math.random() * ICON_W;
		//double randomY = Math.random() * ICON_H * 0.3 + ICON_H * 0.7;
		//objects.add(new Balloon((int)randomX, (int)randomY, SIZE));

		speedSlider = new JSlider(JSlider.VERTICAL,
	            -5, 5, speed);
		speedSlider.setMajorTickSpacing(5);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
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
	
	private void createTimer() {
		timer = new Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				randomDrift = generateDrift();
				for(MovingObject obj : objects) {
					obj.translate(0, -speed, randomDrift);
					//obj.moveTo(0, -speed);
				}
				label.repaint();
			}
		});
	}
	
	private void generateMassBalloons() {
		int balloonNumber = (int) (Math.random() * (7 - 3) + 3);
		System.out.println(balloonNumber);
		for(int number = 0; number <= balloonNumber; number++) {
			double randomX = Math.random() * 0.8*ICON_W + 0.1*ICON_W;
			double randomY = Math.random() * ICON_H * 0.3 + ICON_H * 0.7;
			objects.add(new Balloon((int)randomX, (int) randomY, SIZE));
		}
		//objects.add(new Balloon(20, 30, SIZE, .9));
	}

	private double generateDrift() {
		randomDrift += Math.random() * 0.2 - 0.1;
		if( randomDrift < -10 ) {
			randomDrift = -10;
		} else if( randomDrift > 10) {
			randomDrift = 10;
		}
		return randomDrift;
	}
	
	public void start() {
		timer.start();
	}
}