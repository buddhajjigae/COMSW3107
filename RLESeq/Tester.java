/**
 * The Tester class tests all the methods in RLESeqV1/2/3 and the possible use
 * cases that may occur.
 * 
 * @author Alex Yu ajy2116
 */
public class Tester {
	RLEConverter converter = new RLEConverter();

	/**
	 * This method runs the tests
	 */
	void runTests() {
		int constructorLength = 4;
		int constructorValue = 4;

		// Testing empty constructor + length
		System.out.println("-------EMPTY CONSTRUCTOR-------");
		RLESeqV1 rleseqV1Empty = new RLESeqV1();
		RLESeqV2 rleseqV2Empty = new RLESeqV2();
		RLESeqV3 rleseqV3Empty = new RLESeqV3();
		System.out.println("V1:");
		System.out.println(rleseqV1Empty.toString(rleseqV1Empty));
		System.out.println(rleseqV1Empty.length());
		System.out.println("V2:");
		System.out.println(rleseqV2Empty.toString(rleseqV2Empty));
		System.out.println(rleseqV2Empty.length());
		System.out.println("V3:");
		System.out.println(rleseqV3Empty.toString(rleseqV3Empty));
		System.out.println(rleseqV3Empty.length());

		// Testing length constructor + length
		System.out.println("-------LENGTH CONSTRUCTOR-------");
		RLESeqV1 rleseqV1Length = new RLESeqV1(constructorLength);
		RLESeqV1 rleseqV1Length2 = new RLESeqV1(-1); // Error case
		RLESeqV2 rleseqV2Length = new RLESeqV2(constructorLength);
		RLESeqV3 rleseqV3Length = new RLESeqV3(1921); // Error case
		System.out.println("V1:");
		System.out.println(rleseqV1Length.toString(rleseqV1Length));
		System.out.println(rleseqV1Length.length());
		System.out.println("V2:");
		System.out.println(rleseqV2Length.toString(rleseqV2Length));
		System.out.println(rleseqV2Length.length());
		System.out.println("V3:");
		System.out.println(rleseqV3Length.toString(rleseqV3Length));
		System.out.println(rleseqV3Length.length());

		// Testing value + length constructor + length
		System.out.println("---VALUE + LENGTH CONSTRUCTOR---");
		RLESeqV1 rleseqV1 = new RLESeqV1(constructorLength, constructorValue);
		RLESeqV2 rleseqV2 = new RLESeqV2(constructorLength, constructorValue);
		RLESeqV3 rleseqV3 = new RLESeqV3(constructorLength, constructorValue);
		System.out.println("V1:");
		System.out.println(rleseqV1.toString(rleseqV1));
		System.out.println(rleseqV1.length());
		System.out.println("V2:");
		System.out.println(rleseqV2.toString(rleseqV2));
		System.out.println(rleseqV2.length());
		System.out.println("V3:");
		System.out.println(rleseqV3.toString(rleseqV3));
		System.out.println(rleseqV3.length());

		// V1 Add Tests
		System.out.println("-------V1 ADD TESTS-------");
		rleseqV1.add(-1, 2); // Error Case
		rleseqV1.add(1, 256); // Error case
		rleseqV1.add(1, -1); // Error case
		rleseqV1.add(20, 5); // Error case
		rleseqV1.add(3, 2);
		rleseqV1.add(2, 2);
		rleseqV1.add(0, 255);
		System.out.println(rleseqV1.toString(rleseqV1)); // Should print <255 4 4 2 4 2 4>

		// V2 Add Tests
		System.out.println("-------V2 ADD TESTS-------");
		rleseqV2.add(-1, 2); // Error Case
		rleseqV2.add(1, 256); // Error case
		rleseqV2.add(1, -1); // Error case
		rleseqV2.add(20, 5); // Error case
		rleseqV2.add(3, 2);
		rleseqV2.add(2, 2);
		rleseqV2.add(0, 255);
		System.out.println(rleseqV2.toString(rleseqV2)); // Should print [ (255 1) (4 2) (2 1) (4 1) (2 1) (4 1) ]

		// V3 Add Tests
		System.out.println("-------V3 ADD TESTS-------");
		for (int index = 0; index < 8; index++) {
			RLESeqV3 rleseqv3 = new RLESeqV3(4, 4);
			rleseqv3.add(4, 2);
			rleseqv3.add(4, 2);
			rleseqv3.add(6, 3);
			System.out.println("Insert Index: " + index + " before:" + rleseqv3.toString(rleseqv3));
			rleseqv3.add(index, 5);
			System.out.println("Insert Index: " + index + " after: " + rleseqv3.toString(rleseqv3));
		}

		// V1 Remove tests using <255 4 4 2 4 2 4>
		System.out.println("------V1 REMOVE TESTS------");
		rleseqV1.remove(-1); // Error case
		rleseqV1.remove(7); // Error case
		rleseqV1.remove(3);
		rleseqV1.remove(3);
		System.out.println(rleseqV1.toString(rleseqV1)); // Should print <255 4 4 2 4>

		// V2 Remove tests using [ (255 1) (4 2) (2 1) (4 1) (2 1) (4 1) ]
		System.out.println("------V2 REMOVE TESTS------");
		rleseqV2.remove(-1); // Error case
		rleseqV2.remove(7); // Error case
		rleseqV2.remove(3);
		rleseqV2.remove(3);
		System.out.println(rleseqV2.toString(rleseqV2)); // Should print [ (255 1) (4 2) (2 1) (4 1) ]

		// V3 Remove tests using [ (4 2) (3 2) (4 2) ]
		System.out.println("------V3 REMOVE TESTS------");
		RLESeqV3 rleseqv3 = new RLESeqV3(4, 4);
		rleseqv3.add(2, 3);
		rleseqv3.add(2, 3);
		rleseqv3.remove(0);
		rleseqv3.remove(4);
		rleseqv3.remove(2);
		System.out.println(rleseqv3.toString(rleseqv3)); // Should print [ (4 1) (3 2) (4 1) ]

		// V1 Equals Tests
		System.out.println("------V1 EQUALS TESTS------");
		RLESeqV1 rleseqv1Equals1 = new RLESeqV1(4, 4);
		RLESeqV1 rleseqv1Equals2 = new RLESeqV1(3, 3);
		RLESeqV1 rleseqv1Equals3 = new RLESeqV1(4, 4);
		System.out.println(rleseqv1Equals1.equals(rleseqv1Equals2)); // false
		System.out.println(rleseqv1Equals1.equals(rleseqv1Equals3)); // true

		// V2 Equals Tests
		System.out.println("------V2 EQUALS TESTS------");
		RLESeqV2 rleseqv2Equals1 = new RLESeqV2(4, 4);
		RLESeqV2 rleseqv2Equals2 = new RLESeqV2(3, 3);
		RLESeqV2 rleseqv2Equals3 = new RLESeqV2(4, 4);
		System.out.println(rleseqv2Equals1.equals(rleseqv2Equals2)); // false
		System.out.println(rleseqv2Equals1.equals(rleseqv2Equals3)); // true

		// V3 Equals Tests
		System.out.println("------V3 EQUALS TESTS------");
		RLESeqV3 rleseqv3Equals1 = new RLESeqV3(4, 4);
		RLESeqV3 rleseqv3Equals2 = new RLESeqV3(3, 3);
		RLESeqV3 rleseqv3Equals3 = new RLESeqV3(4, 4);
		System.out.println(rleseqv3Equals1.equals(rleseqv3Equals2)); // false
		System.out.println(rleseqv3Equals1.equals(rleseqv3Equals3)); // true

		// V1 Concat Tests
		System.out.println("------V1 CONCAT TESTS------");
		rleseqV1.concat(rleseqV1);
		System.out.println(rleseqV1.toString(rleseqV1)); // Should print <255 4 4 2 4 255 4 4 2 4>

		// V2 Concat Tests
		System.out.println("------V2 CONCAT TESTS------");
		RLESeqV2 rleseqV2Concat = new RLESeqV2(4, 4);
		rleseqV2.concat(rleseqV2Concat);
		System.out.println(rleseqV2.toString(rleseqV2)); // Should print [ (255 1) (4 2) (2 1) (4 5) ]
		rleseqV2Concat.add(0, 255);
		rleseqV2.concat(rleseqV2Concat);
		System.out.println(rleseqV2.toString(rleseqV2)); // Should print [ (255 1) (4 2) (2 1) (4 5) (255 1) (4 4) ]

		// V3 Concat Tests
		System.out.println("------V3 CONCAT TESTS------");
		RLESeqV3 rleseqV3Concat = new RLESeqV3(4, 4);
		rleseqV3.concat(rleseqV3Concat);
		System.out.println(rleseqV3.toString(rleseqV3)); // Should print [ (4 8) ]
		rleseqV3Concat.add(0, 255);
		rleseqV3.concat(rleseqV3Concat);
		System.out.println(rleseqV3.toString(rleseqV3)); // Should print [ (4 8) (255 1) (4 4) ]
	}
}