import java.util.*;

/**
 * The Thrower class generates the move for each cpu strategy. The strategy to
 * be used will be determined by the human player at the start of the game.The
 * RandomSimThrower, however, is not used/available unless the code in the
 * Runner class is changed.
 * 
 * There are three thrower strategies as subclasses: 
 * 1. RandomSimThrower: Generates a random valid move. 
 * 2. RecorderSimThrower: Generates a move based upon what the opponent has 
 * thrown the most. This is because the opponent is likely to throw what they 
 * have shown to have preference for. 
 * 3. AlphaGoThrower: Generates a move based upon the last move of the opponent
 * and cpu. This thrower will create states as each possible combination of
 * last two moves and give an action value to each possible move for the state.
 * The move chosen will be based upon the action value of the move when encountering 
 * a state. If a minimum action value is not found, it will throw a random move until
 * it gather's enough data (i.e. sufficient action score). Further clarification is 
 * offered at the subclass.
 * 
 * @author Alex Yu ajy2116
 */
public class Thrower {

	/**
	 * This method generates the next move for the cpu thrower based upon which
	 * subclass it is implemented in.
	 * 
	 * @param record the record class object to access records
	 * @param round  the current round
	 * @return returns generated move
	 */
	public String generateThrowerMove(Records record, int round) {
		return "";
	}

	/**
	 * This method takes the moves played in the current round and the outcome of
	 * the round and feeds the AlphaGoSim states with Q values.
	 * 
	 * @param playerMove the player's current move
	 * @param cpuMove    the cpu's current move
	 * @param outcome    the outcome of the round based on the two moves
	 */
	public void feedback(Records record, String outcome, int round) {
	}
}

/**
 * The RandomSimThrower class generates a random move from all possible valid
 * moves. All possible valid moves are given from the Handle class.
 */
class RandomSimThrower extends Thrower {

	public String generateThrowerMove(Records record, int round) {
		int randomMoveCode = (int) (Math.random() * Handle.validMoves.length);
		return Handle.validMoves[randomMoveCode % Handle.validMoves.length];
	}
}

/**
 * The RecorderSimThrower class counts what move the opponent has thrown the
 * most and generates a counter move to that most thrown move.
 */
class RecorderSimThrower extends Thrower {

	public String generateThrowerMove(Records record, int round) {
		int mostThrownMove = -1;
		String throwType = "";
		for (Map.Entry<String, Integer> element : record.playerMoveCounter.entrySet()) {
			int numberOfThrows = ((int) element.getValue());
			if (numberOfThrows >= mostThrownMove) {
				throwType = (String) element.getKey();
				mostThrownMove = numberOfThrows;
			}
		}
		return Handle.recorderCounterMoves.get(throwType);
	}
}

/**
 * The AlphaGoThrower class attempts to generate the best possible move based
 * upon the last move of the player (opponent) and the cpu. If there is no last
 * move in the history of the game, then it will generate a random move. The
 * last two moves will equate to a state and each state will have a
 * corresponding ArrayList of integers that will be action values. The index of
 * that ArrayList will be equivalent a specific move. The action value is the
 * value of throwing that move i.e. the probability that that move will succeed.
 * The action value of a move will decrease or increase based upon what move the
 * AlphaGoThrower has thrown at the current round when it encounters the corresponding state. 
 * Once the AlphaGoThrower finds a minimum action value when it encounters a specific state, 
 * it will throw the move equated to the highest action value for that state.
 * 
 * This strategy allows for the AlphaGoThrower to find the probability of a certain
 * move working after a certain cpu move and player move have been thrown in the
 * last round. As the rounds increase, the thrower will have more/stronger data,
 * allowing it to throw move's with a higher chance of winning.
 * 
 * When going against the Sim used in Part 4 (either Rotator or Reflector strategy),
 * the AlphaGoThrower appears to have a very high win rate. However, this win rate increases
 * and decreases depending on the number of rounds played because the number of rounds will
 * equate to how much data the AlphaGoThrower has access to. So, in cases of when the rounds
 * are low, AlphaGoThrower may not perform as well, or it may perform on the same level
 * as the RandomSimThrower strategy because it will resort to throwing random moves when it
 * does not have sufficient data.
 */
class AlphaGoThrower extends Thrower {
	public static HashMap<String, ArrayList<Integer>> stateActionScores = new HashMap<>();

	/**
	 * Class constructor. This constructor adds every possible combination of last
	 * two moves to the stateActionScores HashMap as a key and the value of each key
	 * is an ArrayList of integers. The key of the hash map will equate to a state
	 * for the AlphaGoThrower. The ArrayList elements will be the action scores and
	 * the index of the ArrayList will be equivalent to a specific move. The
	 * specific move is taken by using the index of the ArrayList and finding the
	 * element at that same index in Handle Class' validMoves.
	 */
	public AlphaGoThrower() {
		for (Map.Entry<String, String> outcome : Handle.outcomes.entrySet()) {
			ArrayList<Integer> actionScores = new ArrayList<Integer>(Collections.nCopies(Handle.validMoves.length, 0));
			stateActionScores.put(outcome.getKey(), actionScores);
		}
	}

	/**
	 * This method will generate a move for the thrower by iterating over the
	 * stateActionScores HashMap and find the key (state) equivalent to the last move of
	 * the player and the cpu. It will then iterate over the ArrayList of action
	 * values for that state and find the highest action value. The index of the
	 * highest action value will equate to a specific move in Handle's valid moves.
	 * It will then throw that specific move if the action value is greater than or
	 * equal to the minimum action value that is preset in the Class. If the highest
	 * action value is not greater than or equal to the minimum action value, then
	 * this method will generate a random throw. It will keep generating random
	 * throws until an action value that equals or surpasses the minimum action value
	 * to the encountered state is found.
	 */
	public String generateThrowerMove(Records record, int round) {
		String move = "";
		/**
		 * This corresponds to the minimum required action value of a move. This can be
		 * changed but to work properly, it should be greater than 1. If you decide to
		 * play for a small amount of round such as 10, then this value should also be
		 * low ex. 1 or 2. Action values are increased/decreased on each round and a low
		 * amount of rounds may not allow for the minimum action value to be met.
		 */
		int minimumActionValue = 5;

		// Generates a random move if there has been less than two rounds played
		int randomMoveIndex = (int) (Math.random() * Handle.validMoves.length);
		move = Handle.validMoves[randomMoveIndex % Handle.validMoves.length];

		// Check to see if minimum rounds have been played
		if (round >= 2) {
			// Obtains the last move of the player and the cpu
			String state = record.playerMoveHistory.get(round - 1) + record.cpuMoveHistory.get(round - 1);
			// Accesses the ArrayList of the corresponding state in the hashmap
			ArrayList<Integer> actionValues = stateActionScores.get(state);
			// Sets the index of the highest value score, the index corresponds to a type of
			// move
			int bestMoveIndex = actionValues.indexOf(Collections.max(actionValues));
			// Check to see if the action value is higher than the minimum action value
			if (actionValues.get(bestMoveIndex).intValue() >= minimumActionValue) {
				// Move will be the specific move found at the action value index in Handle's
				// validMoves
				move = Handle.validMoves[bestMoveIndex];
			}
		}
		return move;
	}

	/**
	 * This method feeds the AlphaGo with necessary data to learn and therefore
	 * throw better moves. It does so by checking the state (last move of the cpu
	 * and player) and checking whether it won lost or tied with the move thrown in
	 * the current round. Depending on the outcome, it will increase the action
	 * value of the current round's move. This allows it to increase or decrease the
	 * value of that move against the specific state so that if it encounter's the
	 * same state again, it can see the value of the move taken in the past against
	 * that state.
	 */
	public void feedback(Records record, String outcome, int round) {
		// Check to see if minimum rounds have been played
		if (round >= 2) {
			// Obtain last move by player and cpu
			String state = record.playerMoveHistory.get(round - 1) + record.cpuMoveHistory.get(round - 1);
			// Accesses the ArrayList of the corresponding state in the hash map
			ArrayList<Integer> actionScores = stateActionScores.get(state);
			int actionIndex = Arrays.asList(Handle.validMoves).indexOf(record.cpuMoveHistory.get(round));
			int oldActionValue = actionScores.get(actionIndex);

			// The Won, Lost, Tied strings are from the player's perspective, not AlphaGo's
			if (outcome.equals("Lost")) {
				int newActionValue = oldActionValue + 1;
				actionScores.set(actionIndex, newActionValue);
			} else if (outcome.equals("Won")) {
				int newActionValue = oldActionValue - 2;
				actionScores.set(actionIndex, newActionValue);
			} else {
				int newActionValue = oldActionValue - 1;
				actionScores.set(actionIndex, newActionValue);
			}
		}
	}
}