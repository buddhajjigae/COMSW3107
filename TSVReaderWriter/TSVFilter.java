
/**
 * The TSVFilter class is based on the Builder Pattern. It allows the building
 * of user defined fields and their respective int or string value. int is used
 * to store potential byte field to be searched in the .tsv source file since
 * the user will have to (byte) cast the value. The builder will build a
 * TSVFilter object using the user defined select values with the build method
 * and the user defined terminal values with the terminate method. The
 * TSVPipeline class uses the built TSVFilter object to then search for the user
 * defined values in the user defined source file. The user can choose to build
 * a TSVFilter object with or without select and with or without terminate.
 * 
 * Note: I decided to have a checkRecord method in this class rather than the
 * TSVPipeline class because I believe it would be better to hide the fields of
 * this class. The only field that can be accessed is the file field which is
 * necessary for TSVPipeline to open the source file to be read. I could have
 * just as easily implemented this check method in the TSVPipeline class but
 * chose not to because of the above reasoning. It does not do anything but
 * check the given arguments against the class fields. The main work is still
 * done by TSVPipeline.
 * 
 * @author Alex Yu ajy2116
 */

public class TSVFilter {
	private final String file;
	private final String field;
	private final String stringValue;
	private final int intValue;
	// These are for the TerminalObservation methods
	private final TerminalObservation option;
	private final String terminalField;
	private String stringMax;
	private int intMax;
	private int terminalCounter = 0;
	private int terminalLineCount = 0;

	/**
	 * Builder class to build the TSVFilter object using user defined values.
	 */
	public static class Builder {
		private String file;
		private String field;
		private String stringValue;
		private int intValue;
		private TerminalObservation option;
		private String terminalField;
		private String stringMax;
		private int intMax;

		/**
		 * Builder method for the user defined .tsv source file.
		 * 
		 * @param file the .tsv source file
		 * 
		 * @return returns the current object instance
		 */
		public Builder sourceFile(String file) {
			this.file = file;
			return this;
		}

		/**
		 * Builder method to assign the field name and value that the user wants to
		 * search for in the source .tsv file.
		 * 
		 * @param field the field name to be searched for
		 * @param value the String value to be searched for
		 * 
		 * @return returns the current object instance
		 */
		public Builder select(String field, String value) {
			this.field = field;
			this.stringValue = value;
			return this;
		}

		/**
		 * Builder method to assign the field name and value that the user wants to
		 * search for in the source .tsv file.
		 * 
		 * @param field the field name to be searched for
		 * @param value the (byte) int value to be searched for
		 * 
		 * @return returns the current object instance
		 */
		public Builder select(String field, int value) {
			this.field = field;
			this.intValue = value;
			return this;
		}

		/**
		 * Builder method for the field name and TerminalObservation option that the
		 * user wants to use to search for in the source .tsv file. Valid options are
		 * MAX and COUNT. If the user does not specify one of these options, the
		 * compiler will give an error to the user.
		 * 
		 * @param field  the field name to be searched for
		 * @param option the terminal option the user wants to use. Options include only
		 *               MAX and COUNT
		 * 
		 * @return returns the current object instance
		 */
		public Builder terminate(String field, TerminalObservation option) {
			this.terminalField = field;
			this.option = option;
			return this;
		}

		/**
		 * Builder method to build the TSVFilter object using what the user has defined.
		 * 
		 * @return returns a TSVFilter object with user specified values
		 */
		public TSVFilter build() {
			return new TSVFilter(this);
		}
	}

	/**
	 * TSVFilter constructor
	 * 
	 * @param builder Builder object
	 */
	private TSVFilter(Builder builder) {
		this.file = builder.file;
		this.field = builder.field;
		this.terminalField = builder.terminalField;
		this.stringValue = builder.stringValue;
		this.intValue = builder.intValue;
		this.option = builder.option;
		this.stringMax = builder.stringMax;
		this.intMax = builder.intMax;
	}

	/**
	 * Helper method to access .tsv source file. Allows TSVPipeline to access the
	 * file field in this class.
	 * 
	 * @return returns the .tsv source file the user specified when building
	 */
	public String getFile() {
		return file;
	}

	/**
	 * Helper method to print out the builder fields of the TSVFilter class. 
	 * 
	 * Note: if the user chooses a String value in select, then age will be printed as 0.
	 * However, if a user chooses an int value in select, then name will be null.
	 * 
	 * @return returns the builder fields of the TSVFilter class as a string
	 */
	@Override
	public String toString() {
		return "Select{" + "field=" + field + ", name=" + stringValue + ", age=" + intValue + ", terminalfield="
				+ terminalField + ", terminaloption=" + option + '}';
	}

	/**
	 * Helper method to check whether the current record given by TSVPipeline
	 * matches the user defined field and value in this class.
	 * 
	 * @param record     the current record to be checked
	 * @param fieldNames the field names of the source file
	 * @param fieldTypes the field types of the source file
	 * 
	 * @return returns whether or not the current record contains the user defined
	 *         select field and value
	 */
	public boolean checkRecord(String[] record, String[] fieldNames, String[] fieldTypes) {
		if (this.field == null) {
			return true;
		}
		for (int index = 0; index < fieldNames.length; index++) {
			if (field.equals(fieldNames[index])) {
				if (fieldTypes[index].equals("byte")) {
					return (intValue == Byte.valueOf(record[index]));
				} else {
					return stringValue.equals(record[index]);
				}
			}
		}
		return false;
	}

	/**
	 * This method will analyze the record and depending on the TerminalObservation
	 * option chosen by the user, it will either increase the terminalCounter if a
	 * matching record is found for COUNT or set the byte/String max value for MAX.
	 * If the option is null, then it will return null.
	 * 
	 * @param record     the current record
	 * @param fieldNames the field names of the source file
	 * @param fieldTypes the field types of the source file
	 */
	public void analyze(String[] record, String[] fieldNames, String[] fieldTypes) {
		if (this.option == null) {
			return;
		}
		for (int index = 0; index < fieldNames.length; index++) {
			if (terminalField.equals(fieldNames[index])) {
				if (option == TerminalObservation.COUNT) {
					terminalCounter++;
				} else if (option == TerminalObservation.MAX) {
					if (fieldTypes[index].equals("byte")) {
						intMax(record, index);
					} else {
						stringMax(record, index);
					}
					terminalLineCount++;
				}
			}
		}
	}

	/**
	 * This helper method will take the index of the matching field type column and 
	 * compare the value found at that index in the record and see if it is the 
	 * highest byte value. It then assigns the current highest byte value to intMax.
	 * 
	 * @param record the current record
	 * @param index  the index of the matching field type column
	 */
	private void intMax(String[] record, int index) {
		int temp = Integer.parseInt(record[index]);

		if (terminalLineCount == 0) {
			intMax = temp;
		} else {
			if (temp > intMax) {
				intMax = temp;
			}
		}
	}

	/**
	 * This helper method will take the index of the matching field type column and
	 * compare the value found at that index in the record and see if it is the
	 * highest String value. It then assigns the current highest String value to
	 * stringMax.
	 * 
	 * @param record the current record
	 * @param index  the index of the matching field type column
	 */
	private void stringMax(String[] record, int index) {
		if (terminalLineCount == 0) {
			stringMax = record[index];
		} else {
			if (record[index].compareTo(stringMax) > 0) {
				stringMax = record[index];
			}
		}
	}

	/**
	 * This method is used to print out the results of the user defined
	 * TerminalObservation option. If a TerminalObservation option has not been
	 * specified, then it will do nothing.
	 */
	public void terminate() {
		if (this.option == null) {
			return;
		}
		if (option.equals(TerminalObservation.COUNT)) {
			System.out.println("Number of terminal records found: " + terminalCounter);
		} else if (option == TerminalObservation.MAX) {
			if (stringMax != null) {
				System.out.println("Terminal max value: " + stringMax);
			} else {
				System.out.println("Terminal max value: " + intMax);
			}
		}
	}
}