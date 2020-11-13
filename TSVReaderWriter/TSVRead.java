
/**
 * The TSVRead class is used to read a valid .tsv file one line at a time. The .tsv file path is given by the user
 * at construction and then checked to see if it exists and if it is valid. When the class object is created,a check 
 * will occur to ensure that the first two lines of the .tsv file are valid (valid if the number of field names is 
 * the same as the number of field types). This check at the construction prevents users from using TSVRead to 
 * read invalid .tsv files. I also separated TSVRead from TSVWrite because I believe that the user should choose 
 * whether or not they will decide to write the record that has been read. This also follows the design style of 
 * other similar classes such as csv reader and csv writer where the two are separated. 
 * 
 * Note: The checks will also prevent multiple tabs in lines and one or both of the first two lines being composed 
 * of only '\n' as being considered a valid .tsv source file. However, it will read any lines composed of only '\n' 
 * after the first two lines but it will not consider them as valid records to be sent to writer. However, this can 
 * easily be changed to allow empty records (composed of only '\n') by changing the check for it in the read() method. 
 * 
 * @author Alex Yu ajy2116
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TSVRead {
	private Scanner scan;
	// Field names are stored to check first two line validity
	private String[] fieldNames;
	// Field types are stored to check first two line validity and subsequent record validity
	private String[] fieldTypes;
	private File sourceFile;
	private boolean validSource = false;
	private int lineCounter = 0;
	private int invalidRecordCount = 0;

	/**
	 * Class constructor that creates a TSVRead object using the user specified
	 * source file path. If source file is not a .tsv file or if source file does
	 * not exist, an exception will be thrown. The constructor will also ensure that
	 * the source file is properly formatted with field names and field types in the
	 * first two lines and therefore a valid source file. See checkFirstTwoLines()
	 * method for more information on validity.
	 * 
	 * @param sourceFilePath the path to the source file that will be read
	 */
	public TSVRead(String sourceFilePath) throws IOException {
		sourceFile = new File(sourceFilePath);
		String isTSV = sourceFilePath.substring(sourceFilePath.length() - 4, sourceFilePath.length());

		if (!isTSV.equals(".tsv")) {
			System.out.println("Source file is not a .tsv file.");
			throw new IOException();
		}
		if (sourceFile.exists()) {
			scan = new Scanner(sourceFile);
			if (!checkFirstTwo()) {
				System.out.println(
						"Number of field names in first line must equal" + "number of field types in second line.");
				throw new IOException();
			}
		} else {
			System.out.println("Source file does not exist.");
			throw new IOException("notExists");
		}
	}

	/**
	 * This method reads a record line returns it if it is valid. Since the class
	 * constructor validates the first two lines, they will always be returned
	 * without checking.
	 * 
	 * @return a valid record or null if current line is not a valid record
	 */
	public String[] read() throws IOException {
		String[] trimmedArray;
		String[] recordArray;
		String record = "";

		if (lineCounter < 2) {
			record = scan.nextLine();
			recordArray = record.split("\t");
			lineCounter++;
			return recordArray;
		} else if (lineCounter >= 2 && scan.hasNextLine()) {
			record = scan.nextLine();
			// Trims the record to ensure that blank spaces do not affect checkRecord method
			trimmedArray = trimRecord(record.split("\t"));
			recordArray = record.split("\t");
			if (checkRecord(trimmedArray) && record != "") {
				lineCounter++;
				return recordArray;
			} else {
				// I chose not to System.out.print to the user that an invalid record has been
				// found because
				// the method returns a record and if it is invalid, the record will be returned
				// as null. Printing
				// constant invalid records may cause clutter and be annoying for the user.
				invalidRecordCount++;
			}
		}
		return null;
	}

	/**
	 * This method checks to see if the first two lines of the file are valid. They
	 * are valid if the number of field names in the first line is the same as the
	 * number of field types in the second line. An exception will be thrown if they
	 * are not of the same length or if one of the first two lines are missing.
	 * 
	 * @return returns true if the first two lines are valid and false if not
	 */
	private boolean checkFirstTwo() throws FileNotFoundException {
		Scanner checkScan = new Scanner(sourceFile);
		String record = "";

		try {
			if (checkScan.hasNextLine()) {
				record = checkScan.nextLine();
				if (!record.isEmpty()) {
					fieldNames = trimRecord(record.split("\t"));
				}
			} else {
				checkScan.close();
				throw new Exception("invalidTSVFile");
			}
			if (checkScan.hasNextLine()) {
				record = checkScan.nextLine();
				if (!record.isEmpty()) {
					fieldTypes = trimRecord(record.split("\t"));
				}
			} else {
				checkScan.close();
				throw new Exception("invalidTSVFile");
			}
			if (fieldNames.length == fieldTypes.length) {
				validSource = true;
			}
		} catch (Exception invalidTSVFile) {
			System.out.println("First line must contain field names and the second line valid field types "
					+ "(String or byte). # of field names must equal # of field types");
			System.exit(1);
		}
		checkScan.close();
		return validSource;
	}

	/**
	 * This method checks to see if the current record that is being read is valid.
	 * A record is valid if it has the same number of elements as the field type
	 * found in line 2 and if 'byte' is a field type in the source file, then the current
	 * record must have bytes in the column that the 'byte' field is in. All other
	 * field types are ignored because a String can be composed of any
	 * character/number and because custom data types such as Foo cannot be
	 * accounted for (there would be infinitely many custom data types). The check
	 * logic, however, can easily be extended to check other data types and ensure
	 * that the record element is of that data type.
	 * 
	 * @param record the current record that is being read
	 * 
	 * @return returns true if the record is valid and false if not
	 */
	private boolean checkRecord(String[] record) {
		boolean valid = false;
		int invalidCount = 0;

		if (record.length == fieldTypes.length) {
			for (int index = 0; index < fieldTypes.length; index++) {
				if (fieldTypes[index].equals("byte") && !checkByte(record[index])) {
					invalidCount++;
				}
			}
		}
		if (invalidCount == 0) {
			valid = true;
		}
		return valid;
	}

	/**
	 * This method checks to see that the current record element that is being read
	 * is a byte or not.
	 * 
	 * @param record the current record element that is being read
	 * 
	 * @return returns true if the record element is a byte and false if not
	 */
	private boolean checkByte(String record) {
		boolean valid = false;

		try {
			byte recordByte = Byte.valueOf(record);
			valid = true;

		} catch (IllegalArgumentException notByte) {
		}
		return valid;
	}

	/**
	 * This method is used to trim the current record of blank spaces to ensure that
	 * the elements in the record can be safely checked without having to account
	 * for blank spaces.
	 * 
	 * @param record the current record that is being read
	 * 
	 * @return returns the current record trimmed of blank spaces for each element
	 *         in the record
	 */
	private String[] trimRecord(String[] record) {
		String[] trimmedRecord = new String[record.length];

		for (int index = 0; index < record.length; index++) {
			trimmedRecord[index] = record[index].trim();
		}
		return trimmedRecord;
	}

	/**
	 * This method is used to close the scanner and output to the user the results
	 * of the read(s). It should be called by the user when they have finished
	 * reading the .tsv file.
	 */
	public void close() throws IOException {
		System.out.println("Lines read: " + lineCounter + " | Corrupted lines: " + invalidRecordCount);
		scan.close();
	}

	/**
	 * This method is used to access whether the class member variable
	 * scanner has a next line to read in the source file or not. It is called by
	 * TSVPipeline to access the scanner of TSVRead.
	 * 
	 * @return returns whether the class member variable scanner has a next line to
	 *         read in the source file or not.
	 */
	public boolean hasNextLine() {
		return scan.hasNextLine();
	}

	/**
	 * This method is a helper method used for testing purposes to print the
	 * contents of the current record array.
	 * 
	 * @param record the current record that is being read
	 */
	private void printRecord(String[] record) {
		for (int index = 0; index < record.length; index++) {
			System.out.println(record[index]);
		}
	}
}