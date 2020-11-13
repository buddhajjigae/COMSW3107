
/**
 * The TSVPipeline class takes the user defined select and or terminal values from
 * TSVFilter and search the source file for them. If the user does not specify anything
 * at all, then it will perform read a valid .tsv file and write the valid records either to
 * System.out or a default output file created using the source file path.  
 * This option is configured when TSVWriter is created in TSVPipeline. Creating TSVWriter
 * with null will send the records to System.out and creating TSVWriter with the default
 * output file path will write the records to the output file. 
 * 
 * Note: Record checking for user specified select and or terminal values is done in
 * TSVFilter. The justification for it is also in the TSVFilter class. 
 * 
 * @author Alex Yu ajy2116
 */

import java.io.IOException;

public class TSVPipeline {
	private String sourcePath;
	private String outputFilePath;
	private TSVRead reader;
	private TSVWrite writer;
	private TSVFilter filter;

	/**
	 * Class constructor. Assigns filter and sourcePath while defining a default
	 * output file path based on the source file path.
	 */
	public TSVPipeline(TSVFilter filter) throws IOException {
		this.filter = filter;
		sourcePath = filter.getFile();
		// Defines a default output file path based on the source file path
		outputFilePath = sourcePath.substring(0, sourcePath.length() - 4) + "-output.tsv";
	}

	/**
	 * This method is used to find the user defined select and or terminate
	 * parameters from TSVFilter in the .tsv source file.
	 * 
	 * Note: specify TSVWrite with null to have the writer print the record or
	 * specify TSVWrite with an output file to have the writer write to the output
	 * file. If null is selected, the field names and field types are also printed.
	 * Comment the null writer and uncomment the outfilePath writer to write to the
	 * output file.
	 * 
	 * @return returns true if a match has been found or false if not
	 */
	public boolean doIt() throws IOException {
		String[] record;
		String[] fields;
		String[] dataTypes;
		int lineCounter = 2;
		boolean found = false;
		reader = new TSVRead(filter.getFile());
		writer = new TSVWrite(null);
		// writer = new TSVWrite(outfilePath);

		fields = reader.read();
		writer.write(fields);
		dataTypes = reader.read();
		writer.write(dataTypes);

		while (reader.hasNextLine()) {
			lineCounter++;
			record = reader.read();
			if (record != null) {
				record = trimRecord(record);
				dataTypes = trimRecord(dataTypes);
				if (filter.checkRecord(record, fields, dataTypes)) {
					System.out.println("---Record found at line: " + lineCounter + "---");
					found = true;
					writer.write(record);
					filter.analyze(record, fields, dataTypes);
				}
			}
		}
		reader.close();
		writer.close();
		filter.terminate();
		return found;
	}

	/**
	 * This method is used to trim the current record of blank spaces to ensure that
	 * the elements in the record can be safely checked without having to account
	 * for blank spaces.
	 * 
	 * @param record the current record
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
	 * Helper method to print a record for testing purposes.
	 * 
	 * @param record the current record
	 */
	private void printRecord(String[] record) {
		for (int index = 0; index < record.length; index++) {
			System.out.println(record[index]);
		}
	}
}