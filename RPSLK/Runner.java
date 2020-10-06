
/**
 * The Runner class runs the game. It first requests the game mode from the user
 * and then runs the game based on the game mode. Game modes offered are human
 * vs cpu and simulated human vs cpu.
 * 
 * @author Alex Yu ajy2116
 */
public class Runner {
	static Talker talker = new Talker();
	Thrower cpu;
	Sim player;
	Records record;

	/**
	 * Class constructor
	 */
	public Runner(Sim player, Thrower cpu) {
		this.cpu = cpu;
		this.player = player;
		record = new Records();
	}

	/**
	 * Runs the game using the selected mode.
	 */
	public void runGame() {
		// The number of rounds to be played in a game sitting, can be changed to preference
		int numOfRounds = 1000;

		for (int round = 0; round < numOfRounds; round++) {
			String playerMove = player.generateNextMove(record, round);
			String cpuMove = cpu.generateThrowerMove(record, round);
			String outcome = Handle.computeOutcome(cpuMove, playerMove);
			System.out.println("Your Move -> " + playerMove + " || " + cpuMove + " <- CPU Move");
			System.out.println("You " + outcome + "!\n");
			record.addRecord(outcome, playerMove, cpuMove);
			cpu.feedback(record, outcome, round);

		}
		record.printThrowCounter();
		record.printWinLoss();
	}

	/**
	 * Runner class driver to run the game. Obtains the user's selected game mode
	 * and then creates the corresponding Sim/Thrower class objects. It then executs
	 * runGame() to run the game.
	 */
	public static void main(String[] args) {
		String gameMode = talker.runTalker();
		Sim player;
		Thrower cpu;
		// The cpu will be in AlphaGoThrower mode
		cpu = new AlphaGoThrower();
		// cpu = new RecorderSimThrower();

		if (gameMode.equals("1") == true) {
			// HumanPlayer mode
			player = new HumanPlayer();
		} else {
			int simMode = (int) (Math.random() * 2);
			if (simMode == 0) {
				// RotatorSim mode
				player = new RotatorSim();
			} else {
				// ReflectorSim mode
				player = new ReflectorSim();
			}
		}
		Runner runner = new Runner(player, cpu);
		runner.runGame();
	}
}