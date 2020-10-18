
/**
 * The RLESeqV2 class represents a sequence of integers ranging from 0 to 255 using a
 * LinkedList. The values X and the number of values Y are represented as a pair (X Y).
 * A LinkedList is used because of its ability to easily insert in between
 * integers while increasing the size of the list while closing gaps and to easily remove 
 * integers and decreasing the size of the list while closing the gaps. This version
 * uses the RLEConverter to perform the add/remove methods. Finally, the maximum size
 * of the sequence is 1920 and the justification for this is that most consumer monitors
 * are 1920x1080 and therefore 1920 represents the max number of horizontal pixels per
 * length pixel. 
 * The Tester class will test possible use cases of this class.
 * 
 * @author Alex Yu ajy2116
 */

import java.util.LinkedList;

public class RLESeqV2 {
	private LinkedList<Integer> sequence = new LinkedList<Integer>();
	RLEConverter converter = new RLEConverter();

	/**
	 * Class constructor that creates a V2 sequence of commonValue's, length times.
	 * If a length of 0 is inputed, an empty V2 is created.
	 * 
	 * @param length      the desired length
	 * @param commonValue the desired value
	 */
	public RLESeqV2(int length, int commonValue) {
		try {
			if (commonValue < 0 || commonValue > 255) {
				throw new IllegalArgumentException("illegalValue");
			} else if (length < 0 || length > 1920) {
				throw new Exception("illegalLength");
			} else if (length == 0) {
			} else {
				sequence.add(commonValue);
				sequence.add(length);
			}
		} catch (IllegalArgumentException illegalValue) {
			System.out.println("Illegal Argument. Value must be between 0 and 255");
		} catch (Exception illegalLength) {
			System.out.println("Illegal Argument. Length must be between 0 and 1920");
		}
	}

	/**
	 * Class constructor that creates a V2 sequence of 0's, length times. 0 is used
	 * as a placeholder object in order to create a sequence of length times.
	 * 
	 * @param length the desired length
	 */
	public RLESeqV2(int length) {
		try {
			if (length < 0 || length > 1920) {
				throw new IllegalArgumentException("illegalLength");
			} else if (length == 0) {
			}
			sequence.add(0);
			sequence.add(length);
		} catch (Exception illegalArgument) {
			System.out.println("Illegal Argument. Value must be between 0 and 1920");
		}
	}

	/**
	 * Class constructor that creates an empty V2 sequence. This is because the
	 * values for pixels should not be set unless they are given by the user.
	 */
	public RLESeqV2() {
	}

	/**
	 * Class constructor that is used by RLEConverter for creating a new V2 from a
	 * V1 sequence.
	 * 
	 * @param plainArray a V2 sequence LinkedList
	 */
	public RLESeqV2(LinkedList<Integer> plainArray) {
		sequence = plainArray;
	}

	/**
	 * This method converts a V2 sequence to a V1 sequence. It is a helper method
	 * that is called by the RLEConverter.
	 * 
	 * @return returns a V1 sequence
	 */
	LinkedList<Integer> toPlainArray() {
		LinkedList<Integer> plainArray = new LinkedList<Integer>();
		int value = -1;
		int valueCount = 0;

		for (int index = 0; index < sequence.size(); index++) {
			if (index % 2 == 0) {
				value = sequence.get(index);
				plainArray.add(value);
			} else {
				valueCount = sequence.get(index);
				for (int counter = 1; counter < valueCount; counter++) {
					plainArray.add(value);
				}
			}
		}
		return plainArray;
	}

	/**
	 * This method returns the length of the V2 sequence using the internal
	 * representation of V2. Note: it does not equal the size of the sequence in V2.
	 * 
	 * @return returns the length of the V2 sequence
	 */
	int length() {
		int sequenceSize = 0;

		for (int count = 1; count < sequence.size(); count += 2) {
			sequenceSize = sequenceSize + sequence.get(count);
		}
		return sequenceSize;
	}

	/**
	 * This method adds a value at an index into the V2 sequence by first converting
	 * the V2 into a V1 then using the add() method in V1 to accomplish the add and
	 * then converting the V1 back into a V2.
	 * 
	 * @param index the desired index
	 * @param value the desired value
	 */
	void add(int index, int value) {
		RLESeqV1 rleseqv1 = new RLESeqV1();
		RLEConverter converter = new RLEConverter();
		rleseqv1 = converter.toV1(this);
		try {
			if (value < 0 || value > 255) {
				throw new IllegalArgumentException("illegalArgument");
			} else if (index < 0 || index > rleseqv1.length()) {
				throw new IndexOutOfBoundsException("indexOutOfBounds");
			} else if (sequence.size() == 1920) {
				throw new Exception("maxSize");
			}
			rleseqv1.add(index, value);
			RLESeqV2 temp = converter.toV2(rleseqv1);
			this.sequence = temp.sequence;
		} catch (IndexOutOfBoundsException indexOutOfBounds) {
			System.out.println("Index Out of Bounds Error. Index must be between 0 and " + rleseqv1.length());
		} catch (IllegalArgumentException illegalArgument) {
			System.out.println("Illegal Argument Error. Value must be between 0 and 255");
		} catch (Exception maxSize) {
			System.out.println("Maximum Size Reached Error. The RLESeq cannot be added to any more.");
		}
	}

	/**
	 * This method removes a value at an index from the V2 sequence. It does so by
	 * using first converting the V2 sequence into a V1 sequence and then using the
	 * V1 remove() to remove the index value and then convert back to a V2 sequence
	 * using the RLEConverter.
	 * 
	 * @param index the desired index
	 */
	void remove(int index) {
		RLESeqV1 rleseqv1 = new RLESeqV1();
		RLEConverter converter = new RLEConverter();
		rleseqv1 = converter.toV1(this);
		try {
			if (index > rleseqv1.length() || index < 0) {
				throw new IndexOutOfBoundsException("indexOutOfBounds");
			} else if (rleseqv1.length() == 0) {
				throw new IllegalArgumentException("illegalArgument");
			}
			rleseqv1.remove(index);
			RLESeqV2 temp = converter.toV2(rleseqv1);
			this.sequence = temp.sequence;
		} catch (IllegalArgumentException illegalArgument) {
			System.out.println("Nothing to Remove. The RLESeq is empty.");
		} catch (IndexOutOfBoundsException indexOutOfBounds) {
			System.out.println("Index Out of Bounds Error. Index must be between 0 and " + (rleseqv1.length() - 1));
		}
	}

	/**
	 * This method checks whether two V2 objects are the same. Note: Be careful not
	 * to have two differently named RLESeqV2 objects point to the same sequence as
	 * they will always equal each other. It will also use the V2 representation to
	 * determine if the two V2 objects are the same.
	 * 
	 * @param rleseq RLESeqV2 object
	 * @return true or false depending on whether the two RLESeqV2's are the same
	 */
	boolean equals(RLESeqV2 rleseq) {
		return sequence.equals(rleseq.getSequence(rleseq));
	}

	/**
	 * This method concatenates the calling V2 sequence with another V2 sequence. If
	 * the last value of the first V2 is the same as the first value of the second
	 * V2, it will combine them into one pair and increase the count of the values
	 * accordingly. Note: Concatenating two V2 that point to the same object will
	 * cause it to not work properly.
	 * 
	 * @param rleseq RLESeqV2 object
	 */
	void concat(RLESeqV2 rleseq) {
		// Checks to see if the last and first value are the same
		if (sequence.get(sequence.size() - 2) == rleseq.getSequence(rleseq).get(0)) {
			sequence.set(sequence.size() - 1, sequence.get(sequence.size() - 1) + rleseq.getSequence(rleseq).get(1));
			if (rleseq.getSequence(rleseq).size() == 2) {
			} else {
				sequence.addAll(2, rleseq.getSequence(rleseq));
			}
		} else {
			sequence.addAll(rleseq.getSequence(rleseq));
		}
	}

	/**
	 * This method returns a String of the V2 sequence values using the V2
	 * representation of (value valueCount).
	 * 
	 * @param rleseq an RLESeqV2 object
	 * @return returns a String of the V2 sequence values
	 */
	String toString(RLESeqV2 rleseq) {
		String sequenceString = "[ ";
		for (int index = 0; index < rleseq.sequence.size(); index++) {
			if (index % 2 == 0) {
				sequenceString = sequenceString + "(" + rleseq.sequence.get(index) + " ";
			} else {
				sequenceString = sequenceString + rleseq.sequence.get(index) + ") ";
			}
		}
		sequenceString = sequenceString + "]";
		return sequenceString;
	}

	/**
	 * This method is a helper method used to access the private sequence of the
	 * class
	 * 
	 * @param rleseq an RLESeqV2 object
	 * @return returns the sequence
	 */
	private LinkedList<Integer> getSequence(RLESeqV2 rleseq) {
		return rleseq.sequence;
	}
}