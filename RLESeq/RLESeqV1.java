
/**
 * The RLESeqV1 class represents a sequence of integers ranging from 0 to 255 using a
 * LinkedList. A LinkedList is used because of its ability to easily insert in between
 * integers while increasing the size of the list while closing gaps and to easily remove 
 * integers and decreasing the size of the list while closing the gaps. This version
 * uses the java api methods to perform the required methods. Finally, the maximum size
 * of the sequence is 1920 and the justification for this is that most consumer monitors
 * are 1920x1080 and therefore 1920 represents the max number of horizontal pixels per
 * length pixel. 
 * The Tester class will test possible use cases of this class.
 * 
 * @author Alex Yu ajy2116
 */

import java.util.LinkedList;

public class RLESeqV1 {
	private LinkedList<Integer> sequence = new LinkedList<Integer>();

	/**
	 * Class constructor that creates a V1 sequence of commonValue's, length times.
	 * If a length of 0 is inputed, an empty V1 is created.
	 * 
	 * @param length      the desired length
	 * @param commonValue the desired value
	 */
	public RLESeqV1(int length, int commonValue) {
		try {
			if (commonValue < 0 || commonValue > 255) {
				throw new IllegalArgumentException("illegalValue");
			} else if (length < 0 || length > 1920) {
				throw new Exception("illegalLength");
			}
			for (int index = 0; index < length; index++) {
				sequence.add(commonValue);
			}
		} catch (IllegalArgumentException illegalValue) {
			System.out.println("Illegal Argument. Value must be between 0 and 255");
		} catch (Exception illegalLength) {
			System.out.println("Illegal Argument. Length must be between 0 and 1920");
		}
	}

	/**
	 * Class constructor that creates a V1 sequence of 0's, length times. 0 is used
	 * as a placeholder object in order to create a sequence of length times.
	 * 
	 * @param length the desired length
	 */
	public RLESeqV1(int length) {
		try {
			if (length < 0 || length > 1920) {
				throw new IllegalArgumentException("illegalLength");
			} else if (length == 0) {
			}
			for (int index = 0; index < length; index++) {
				sequence.add(0);
			}
		} catch (Exception illegalArgument) {
			System.out.println("Illegal Argument. Value must be between 0 and 1920");
		}
	}

	/**
	 * Class constructor that creates an empty V1 sequence. This is because the
	 * values for pixels should not be set unless they are given by the user.
	 */
	public RLESeqV1() {
	}

	/**
	 * Class constructor that is used by RLEConverter for creating a new V1 from a
	 * V2 sequence.
	 * 
	 * @param plainArray a V1 sequence LinkedList
	 */
	public RLESeqV1(LinkedList<Integer> plainArray) {
		sequence = plainArray;
	}

	/**
	 * This method converts a V1 sequence to a V2 sequence. It is a helper method
	 * that is called by the RLEConverter.
	 * 
	 * @return returns a V2 sequence
	 */
	LinkedList<Integer> toPlainArray() {
		LinkedList<Integer> plainArray = new LinkedList<Integer>();
		int currentValue = -1;
		int counter = 0;
		int counterIndex = 1;

		for (Integer value : sequence) {
			if (value.intValue() != currentValue) {
				plainArray.add(value);
				currentValue = value;
				plainArray.add(counterIndex, 1);
				counter = counterIndex;
				counterIndex += 2;
			} else {
				plainArray.set(counter, plainArray.get(counter) + 1);
			}
		}
		return plainArray;
	}

	/**
	 * This method returns the length of the V1 sequence using the java api.
	 * 
	 * @return returns the length of the V1 sequence
	 */
	int length() {
		return sequence.size();
	}

	/**
	 * This method adds a value at an index into the V1 sequence using the java api.
	 * 
	 * @param index the desired index
	 * @param value the desired value
	 */
	void add(int index, int value) {
		try {
			if (value < 0 || value > 255) {
				throw new IllegalArgumentException("illegalArgument");
			} else if (index < 0 || index > sequence.size()) {
				throw new IndexOutOfBoundsException("indexOutOfBounds");
			} else if (sequence.size() == 1920) {
				throw new Exception("maxSize");
			}
			sequence.add(index, value);
		} catch (IndexOutOfBoundsException indexOutOfBounds) {
			System.out.println("Index Out of Bounds Error. Index must be between 0 and " + sequence.size());
		} catch (IllegalArgumentException illegalArgument) {
			System.out.println("Illegal Argument Error. Value must be between 0 and 255");
		} catch (Exception maxSize) {
			System.out.println("Maximum Size Reached Error. The RLESeq cannot be added to any more.");
		}
	}

	/**
	 * This method removes a value at an index from the V1 sequence using the java
	 * api.
	 * 
	 * @param index the desired index
	 */
	void remove(int index) {
		try {
			if (index > sequence.size() || index < 0) {
				throw new IndexOutOfBoundsException("indexOutOfBounds");
			} else if (sequence.size() == 0) {
				throw new IllegalArgumentException("illegalArgument");
			}
			sequence.remove(index);
		} catch (IllegalArgumentException illegalArgument) {
			System.out.println("Nothing to Remove. The RLESeq is empty.");
		} catch (IndexOutOfBoundsException indexOutOfBounds) {
			System.out.println("Index Out of Bounds Error. Index must be between 0 and " + (sequence.size() - 1));
		}
	}

	/**
	 * This method checks whether two V1 objects are the same. Note: Be careful not
	 * to have two differently named RLESeqV1 objects point to the same sequence as
	 * they will always equal each other.
	 * 
	 * @param rleseq RLESeqV1 object
	 * @return true or false depending on whether the two RLESeqV1's are the same
	 */
	boolean equals(RLESeqV1 rleseq) {
		return sequence.equals(rleseq.getSequence(rleseq));
	}

	/**
	 * This method concatenates the calling V1 sequence with another V1 sequence.
	 * 
	 * @param rleseq RLESeqV1 object
	 */
	void concat(RLESeqV1 rleseq) {
		sequence.addAll(rleseq.getSequence(rleseq));
	}

	/**
	 * This method returns a String of the V1 sequence values.
	 * 
	 * @param rleseq an RLESeqV1 object
	 * @return returns a String of the V1 sequence values
	 */
	String toString(RLESeqV1 rleseq) {
		String seqToString = "<";

		for (int index = 0; index < rleseq.sequence.size(); index++) {
			seqToString = seqToString + " " + rleseq.sequence.get(index);
		}
		seqToString = seqToString + " >";
		return seqToString;
	}

	/**
	 * This method is a helper method used to access the private sequence of the
	 * class
	 * 
	 * @param rleseq an RLESeqV1 object
	 * @return returns the sequence
	 */
	private LinkedList<Integer> getSequence(RLESeqV1 rleseq) {
		return rleseq.sequence;
	}
}