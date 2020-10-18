/**
 * The Runner class simply runs the Tester class by calling the runTests()
 * method in Tester. The runTests() method will test each method from the
 * RLESeq classes and possible use cases to ensure that they are working
 * properly. 
 * 
 * @author Alex Yu ajy2116
 */
public class Runner {
	public static void main(String[] args) {
		Tester tester = new Tester();
		tester.runTests();
	}
}