/**
 * The RLEConverter class takes an RLESeqV1/2 and converts it to the opposite
 * version. It accomplishes this by calling a helper method in the version to be
 * converted which returns a LinkedList that has been changed to the opposite
 * version and the creates a new object of the version desired.
 * 
 * @author Alex Yu ajy2116
 */

public class RLEConverter {

	/**
	 * This method converts a V2 into a new V1 object. It does so by calling the
	 * helper method toPlainArray() which returns a V1 sequence. 
	 * 
	 * @return a new V1 object that has been converted from a V2 object
	 */
	RLESeqV1 toV1(RLESeqV2 rleseqv2) {
		return new RLESeqV1(rleseqv2.toPlainArray());
	}

	/**
	 * This method converts a V1 into a new V2 object. It does so by calling the
	 * helper method toPlainArray() which returns a V2 sequence. 
	 * 
	 * @return a new V2 object that has been converted from a V1 object
	 */
	RLESeqV2 toV2(RLESeqV1 rleseqv1) {
		return new RLESeqV2(rleseqv1.toPlainArray());
	}
}