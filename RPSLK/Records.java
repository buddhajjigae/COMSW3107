import java.util.*;

/**
 * The Records class keeps track of all the necessary records for the game. It
 * will tally the total wins/losses/ties/rounds and also maintain the history of
 * moves for both the cpu and the human/sim human.
 * 
 * @author Alex Yu ajy2116
 */
public class Records {
	LinkedHashMap<String, Integer> playerMoveCounter = new LinkedHashMap<String, Integer>();
	LinkedHashMap<String, Integer> cpuMoveCounter = new LinkedHashMap<String, Integer>();
	ArrayList<String> playerMoveHistory = new ArrayList<String>();
	ArrayList<String> cpuMoveHistory = new ArrayList<String>();
	int wins = 0;
	int losses = 0;
	int ties = 0;
	int rounds = 0;

	/**
	 * Class constructor that creates a counter for each possible move for the
	 * player and cpu
	 */
	public Records() {
		playerMoveCounter.put("R", 0);
		playerMoveCounter.put("P", 0);
		playerMoveCounter.put("S", 0);
		playerMoveCounter.put("K", 0);
		playerMoveCounter.put("L", 0);

		cpuMoveCounter.put("R", 0);
		cpuMoveCounter.put("P", 0);
		cpuMoveCounter.put("S", 0);
		cpuMoveCounter.put("K", 0);
		cpuMoveCounter.put("L", 0);
	}

	/**
	 * Adds both the cpu and the human/sim human's current move to their respective
	 * history ArrayList's for record keeping. These ArrayList's will be used in
	 * certain sim human/cpu throw strategies to calculate the next move of those
	 * strategies. It will also keep track of the total wins/losses/ties/rounds of
	 * the game.
	 * 
	 * @param outcome    the outcome of the current game round
	 * @param playerMove the human or sim human's current move
	 * @param cpuMove    the cpu's current move
	 */
	public void addRecord(String outcome, String playerMove, String cpuMove) {
		cpuMoveHistory.add(cpuMove);
		playerMoveHistory.add(playerMove);
		playerMoveCounter.put(playerMove, playerMoveCounter.get(playerMove) + 1);
		cpuMoveCounter.put(cpuMove, cpuMoveCounter.get(cpuMove) + 1);
		rounds += 1;

		if (outcome.equals("Won")) {
			wins += 1;
		} else if (outcome.equals("Lost")) {
			losses += 1;
		} else {
			ties += 1;
		}
	}

	/**
	 * Prints the total amount of cpu and player throws for each type of throw for
	 * the entire game history. It uses hard coded LinkedHashMap's to avoid extra
	 * computing time by iterating over the entire move histories.
	 */
	public void printThrowCounter() {
		System.out.println("Player's total throws:");
		for (Map.Entry<String, Integer> element : playerMoveCounter.entrySet()) {
			String throwType = (String) element.getKey();
			int value = ((int) element.getValue());
			System.out.println(throwType + " : " + value);
		}

		System.out.println("CPU's total throws:");
		for (Map.Entry<String, Integer> element : cpuMoveCounter.entrySet()) {
			String throwType = (String) element.getKey();
			int value = ((int) element.getValue());
			System.out.println(throwType + " : " + value);
		}
	}

	/**
	 * Prints the complete win/loss/tie record for the game.
	 */
	public void printWinLoss() {
		System.out.println("Wins: " + String.valueOf(wins) + " Losses: " + String.valueOf(losses) + " Ties: "
				+ String.valueOf(ties));
	}
}