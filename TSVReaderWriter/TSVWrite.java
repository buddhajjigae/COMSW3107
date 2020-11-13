
/**
 * The TSVWrite class take a record and writes it to a user defined output file. If a user has 
 * not specified an output file, it will print the record to System.out instead. I separated 
 * TSVWrite from TSVRead because I believe that the user should choose whether or not they will 
 * decide to write the record that has been read. This also follows the design style of other 
 * similar classes such as csv reader and csv writer where the two are separated. 
 * 
 * @author Alex Yu ajy2116
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TSVWrite {
	private File outputFile;
	private FileWriter writer;
	private int lineCounter = 0;

	/**
	 * Class constructor that creates a TSVWrite object using the user specified
	 * output file path. If output file is not a .tsv file an exception will be
	 * thrown. The constructor will check if the output file exists and if it does,
	 * it will use the existing output file and write over it.
	 * 
	 * Note: writer is set to null if the user does not define an output file path
	 * and instead, uses null as an argument. This allows the TSVPipeline to use
	 * writer except that writer will not write to an output file but, instead,
	 * print out to the user the record.
	 * 
	 * @param outputFilePath the path to the output file that will be written to
	 */
	public TSVWrite(String outputFilePath) throws IOException {
		if (outputFilePath != null) {
			outputFile = new File(outputFilePath);
			writer = new FileWriter(outputFile);
			String isTSV = outputFilePath.substring(outputFilePath.length() - 4, outputFilePath.length());

			if (!isTSV.equals(".tsv")) {
				System.out.println("Output file is not a .tsv file. Please specify a .tsv file.");
				throw new IOException();
			}
			if (outputFile.createNewFile()) {
				System.out.println("Output file successfully created at: " + outputFilePath);
			} else {
				System.out.println("Output file already exists. Existing file will be overidden.");
			}
		} else {
			writer = null;
		}
	}

	/**
	 * This method will write the current record to the output file if an output
	 * file has been specified. If an output file has not been specified and writer
	 * is null, then it will print the record to System.out.
	 * 
	 * @param record the current record
	 */
	public void write(String[] record) throws IOException {
		String recordString = String.join("\t", record);

		if (writer != null) {
			writer.write(recordString + "\n");
			writer.flush();
		} else {
			System.out.println(recordString);
		}
		lineCounter++;
	}

	/**
	 * Method to close the writer and print to the user how many lines have been
	 * written. It should be called by the user when they have finished writing to a
	 * file.
	 */
	public void close() throws IOException {
		System.out.println("Lines written: " + lineCounter);
		if (writer != null) {
			writer.close();
		}
	}
}