
/**
 * The RLESeqV3 class represents a sequence of integers ranging from 0 to 255 using a
 * LinkedList. The values X and the number of values Y are represented as a pair (X Y).
 * A LinkedList is used because of its ability to easily insert in between
 * integers while increasing the size of the list while closing gaps and to easily remove 
 * integers and decreasing the size of the list while closing the gaps. This version
 * performs the methods internally without using the RLEConverter. Finally, the maximum size
 * of the sequence is 1920 and the justification for this is that most consumer monitors
 * are 1920x1080 and therefore 1920 represents the max number of horizontal pixels per
 * length pixel. 
 * The Tester class will test possible use cases of this class.
 * 
 * @author Alex Yu ajy2116
 */
import java.util.LinkedList;
import java.util.ListIterator;

public class RLESeqV3 {
	private LinkedList<Integer> sequence = new LinkedList<Integer>();

	/**
	 * Class constructor that creates a V3 sequence of commonValue's, length times.
	 * If a length of 0 is inputed, an empty V3 is created.
	 * 
	 * @param length      the desired length
	 * @param commonValue the desired value
	 */
	public RLESeqV3(int length, int commonValue) {
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
	 * Class constructor that creates a V3 sequence of 0's, length times. 0 is used
	 * as a placeholder object in order to create a sequence of length times.
	 * 
	 * @param length the desired length
	 */
	public RLESeqV3(int length) {
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
	 * Class constructor that creates an empty V3 sequence. This is because the
	 * values for pixels should not be set unless they are given by the user.
	 */
	public RLESeqV3() {
	}

	/**
	 * This method returns the length of the V3 sequence using the internal
	 * representation of V3. Note: it does not equal the size of the sequence in V3.
	 * 
	 * @return returns the length of the V3 sequence
	 */
	int length() {
		int sequenceSize = 0;

		for (int count = 1; count < sequence.size(); count += 2) {
			sequenceSize = sequenceSize + sequence.get(count);
		}
		return sequenceSize;
	}

	/**
	 * This method adds a value at an index into the V3 sequence directly without
	 * converting it into a V1 first and then back. To do so, it iterates over pairs
	 * of value's and their counts and determines whether the index is in the pair
	 * or not. If it is in the pair, depending on the index, it will either add a
	 * new pair at the location, break an existing pair first and then add the new
	 * pair, or increase the value count of an existing pair.
	 * 
	 * @param index the desired index
	 * @param value the desired value
	 */
	void add(int index, int value) {
		try {
			if (value < 0 || value > 255) {
				throw new IllegalArgumentException("illegalArgument");
			} else if (index < 0 || index > this.length()) {
				throw new IndexOutOfBoundsException("indexOutOfBounds");
			} else if (sequence.size() == 1920) {
				throw new Exception("maxSize");
			}
			// Using ListIterator to be able to directly add elements during iteration
			ListIterator<Integer> iterator = sequence.listIterator();
			// The index of the pair of value and the value count
			int v3index = 0;
			// The number of elements
			int valueSum = 0;

			// Adds at end of sequence
			if (index == this.length()) {
				// If value does not exist at end, adds new pair
				if (sequence.get(sequence.size() - 2) != value) {
					sequence.add(value);
					sequence.add(1);
					// Value exists, alter last pair value count
				} else {
					int tempValue = sequence.get(sequence.size() - 1);
					sequence.set(sequence.size() - 1, tempValue + 1);
				}
				// Add at beginning of sequence
			} else if (index == 0) {
				sequence.add(0, value);
				sequence.add(1, 1);
			} else {
				while (iterator.hasNext()) {
					// Obtains the value and value count pair
					int pairValue = iterator.next();
					int count = iterator.next();

					// Check if at the pair containing the index
					if (valueSum + count > index) {
						if (pairValue == value) {
							sequence.set(v3index * 2 + 1, sequence.get(v3index * 2 + 1) + 1);
						} else {
							// Removal of first pair in split as its value count will be 0
							if (index - valueSum == 0) {
								iterator.add(value);
								iterator.add(1);
								iterator.add(pairValue);
								iterator.add((valueSum + count) - index);
								sequence.remove(v3index * 2 + 1);
								sequence.remove(v3index * 2);
								// Removal of second pair in split as its value count will be 0
							} else if (valueSum - count == 0) {
								iterator.add(value);
								iterator.add(1);
								sequence.set(v3index * 1, index - valueSum);
								sequence.remove(v3index * 2 + 2);
								sequence.remove(v3index * 2 + 3);
								// No removal, only split the pair and add in between
							} else {
								iterator.add(value);
								iterator.add(1);
								iterator.add(pairValue);
								iterator.add((valueSum + count) - index);
								sequence.set(v3index * 2 + 1, index - valueSum);
							}
						}
						break;
					}
					v3index++;
					valueSum += count;
				}
			}
		} catch (IndexOutOfBoundsException indexOutOfBounds) {
			System.out.println("Index Out of Bounds Error. Index must be between 0 and " + this.length());
		} catch (IllegalArgumentException illegalArgument) {
			System.out.println("Illegal Argument Error. Value must be between 0 and 255");
		} catch (Exception maxSize) {
			System.out.println("Maximum Size Reached Error. The RLESeq cannot be added to any more.");
		}
	}

	/**
	 * This method removes a value at an index from the V3 sequence directly without
	 * using the RLEConverter. It uses the V3 representation of value and value
	 * count pairs.
	 * 
	 * @param index the desired index
	 */
	void remove(int index) {
		try {
			if (this.length() - 1 < index || index < 0) {
				throw new IndexOutOfBoundsException("indexOutOfBounds");
			} else if (this.length() == 0) {
				throw new IllegalArgumentException("illegalArgument");
			}
			ListIterator<Integer> iterator = sequence.listIterator();
			int v3index = 0;
			int valueSum = 0;

			if (index == (this.length() - 1)) {
				int lastCount = sequence.size() - 1;
				// Removes the last pair if the value count will become 0
				if (sequence.get(lastCount) - 1 == 0) {
					sequence.remove(sequence.size() - 1);
					sequence.remove(sequence.size() - 1);
				} else {
					// Lowers the value count of the last pair by 1
					sequence.set(lastCount, sequence.get(lastCount) - 1);
				}
			} else {
				while (iterator.hasNext()) {
					// Although not used, pairValue is necessary for the iterator to obtain a pair
					int pairValue = iterator.next();
					int count = iterator.next();

					// Check if at the pair containing the index
					if (valueSum + count > index) {
						// Removes the pair if the value count will become 0
						if (sequence.get(v3index * 2 + 1) - 1 == 0) {
							sequence.remove(v3index * 2 + 1);
							sequence.remove(v3index * 2);
							// Lowers the value count of the pair by 1
						} else {
							sequence.set(v3index * 2 + 1, sequence.get(v3index * 2 + 1) - 1);
						}
						break;
					}
					v3index++;
					valueSum += count;
				}
			}
		} catch (IllegalArgumentException illegalArgument) {
			System.out.println("Nothing to Remove. The RLESeq is empty.");
		} catch (IndexOutOfBoundsException indexOutOfBounds) {
			System.out.println("Index Out of Bounds Error. Index must be between 0 and " + (this.length() - 1));
		}
	}

	/**
	 * This method checks whether two V3 objects are the same. Note: Be careful not
	 * to have two differently named RLESeqV3 objects point to the same sequence as
	 * they will always equal each other. It will also use the V3 representation to
	 * determine if the two V3 objects are the same.
	 * 
	 * @param rleseq RLESeqV3 object
	 * @return true or false depending on whether the two RLESeqV3's are the same
	 */
	boolean equals(RLESeqV3 rleseq) {
		return sequence.equals(rleseq.getSequence(rleseq));
	}

	/**
	 * This method concatenates the calling V3 sequence with another V3 sequence. If
	 * the last value of the first V3 is the same as the first value of the second
	 * V3, it will combine them into one pair and increase the count of the values
	 * accordingly. Note: Concatenating two V3 that point to the same object will
	 * cause it to not work properly.
	 * 
	 * @param rleseq RLESeqV3 object
	 */
	void concat(RLESeqV3 rleseq) {
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
	 * This method returns a String of the V3 sequence values using the V3
	 * representation of (value valueCount).
	 * 
	 * @param rleseq an RLESeqV3 object
	 * @return returns a String of the V3 sequence values
	 */
	String toString(RLESeqV3 rleseq) {
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
	 * @param rleseq an RLESeqV3 object
	 * @return returns the sequence
	 */
	private LinkedList<Integer> getSequence(RLESeqV3 rleseq) {
		return rleseq.sequence;
	}
}