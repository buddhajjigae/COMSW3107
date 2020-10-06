import java.util.Arrays;
import java.util.Scanner;

/**
 * The Talker class gives an introduction to the game along with the rules for
 * it. It will also obtain the game mode from the user. The game mode will
 * decide whether a human plays versus the cpu or a simulated human plays versus
 * the cpu.
 * 
 * @author Alex Yu ajy2116
 */
public class Talker {
	Scanner scan = new Scanner(System.in);

	/**
	 * Runs these three methods within the talker and returns the player selected
	 * game mode.
	 */
	public String runTalker() {
		printWelcomeMessage();
		printRules();
		return obtainGameMode();
	}

	/**
	 * Prints the welcome message.
	 */
	public void printWelcomeMessage() {
		System.out.println("############################################");
		System.out.println("Welcome to Rock Paper Scissors Lizard Spock!");
		System.out.println("############################################\n");

	}

	/**
	 * Prints the rules of the game.
	 */
	public void printRules() {
		System.out.println("---RULES TO THE GAME---");
		System.out.println("Rock beats Scissors.");
		System.out.println("Paper beats Rock.");
		System.out.println("Rock beats Lizard.");
		System.out.println("Lizard beats Spock.");
		System.out.println("Spock beats Scissors.");
		System.out.println("Scissors beats Lizard.");
		System.out.println("Lizard beats Paper.");
		System.out.println("Paper beats Spock.");
		System.out.println("Spock beats Rock.");
		System.out.println("Moves of the same type results in a draw.\n");
	}

	/**
	 * Obtains the game mode from the user.
	 */
	public String obtainGameMode() {
		String gameMode = "";
		boolean validMode = false;
		System.out.println("Please select the game mode:");
		System.out.println("1: Human player mode || 2: Sim player mode");

		while (validMode == false) {
			gameMode = scan.next();
			if (Arrays.asList(Handle.gameModes).contains(gameMode) == false) {
				System.out.println("Please enter a valid game mode.");
			} else {
				validMode = true;
			}
		}
		return gameMode;
	}
}