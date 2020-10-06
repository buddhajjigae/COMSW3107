import java.util.*;

/**
 * The Handle class takes care of all the game logic. It will validate whether
 * the user input is a valid move or not and whether the user selected game mode
 * is valid or not. It will also compute the outcome of each round. Finally, it
 * also determine's the rotator sim's best counter move. The rotator sim's best
 * counter moves are hard coded as valid counter's to possible opponent moves.
 * There are, however, other potential counter's to each throw type in RPSLK but
 * only one potential counter is chosen for this program to simplify the game
 * logic.
 * 
 * @author Alex Yu ajy2116
 */
public class Handle {
	public static HashMap<String, String> outcomes = new HashMap<>();
	public static HashMap<String, String> recorderCounterMoves = new HashMap<>();
	public static String[] gameModes = { "1", "2" };
	public static String[] validMoves = { "R", "P", "S", "L", "K" };

	/**
	 * These outcome rules and rotator sim counter moves are statically initialized
	 * because the rules/winning counter moves's will not change.
	 */
	static {
		// Outcomes for RPS
		outcomes.put("RR", "Tied");
		outcomes.put("RP", "Lost");
		outcomes.put("RS", "Won");
		outcomes.put("SS", "Tied");
		outcomes.put("SR", "Lost");
		outcomes.put("SP", "Won");
		outcomes.put("PP", "Tied");
		outcomes.put("PR", "Won");
		outcomes.put("PS", "Lost");
		// Extends the outcomes to RPSLK
		outcomes.put("RL", "Won");
		outcomes.put("RK", "Lost");
		outcomes.put("SL", "Won");
		outcomes.put("SK", "Lost");
		outcomes.put("PL", "Lost");
		outcomes.put("PK", "Won");
		outcomes.put("LL", "Tied");
		outcomes.put("LR", "Lost");
		outcomes.put("LP", "Won");
		outcomes.put("LS", "Lost");
		outcomes.put("LK", "Won");
		outcomes.put("KK", "Tied");
		outcomes.put("KR", "Won");
		outcomes.put("KP", "Lost");
		outcomes.put("KS", "Won");
		outcomes.put("KL", "Lost");
		// What the RotatorSimThrower should counter with
		recorderCounterMoves.put("R", "P");
		recorderCounterMoves.put("P", "S");
		recorderCounterMoves.put("S", "K");
		recorderCounterMoves.put("K", "L");
		recorderCounterMoves.put("L", "R");
	}

	/**
	 * This method computes the outcome of each round of the game for all game
	 * modes.
	 * 
	 * @param cpuMove    the cpu's move for the round
	 * @param playerMove the human player or sim human's move for the round
	 * @return the outcome of the round, in the perspective of the human/sim human
	 */
	public static String computeOutcome(String cpuMove, String playerMove) {
		String outcome = outcomes.get(playerMove + cpuMove);
		return outcome;
	}
}