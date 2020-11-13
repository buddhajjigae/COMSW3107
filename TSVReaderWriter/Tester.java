
/**
 * The Tester class is used to test the TSV classes.
 * 
 * @author Alex Yu ajy2116
 */

import java.io.IOException;

public class Tester {

	/**
	 * This method contains test cases.
	 */
	public void test() throws IOException {
		String[] record;
		String sourceFile = "C:\\Users\\User\\eclipse-workspace\\CC-Assig3\\test.tsv";
		String outputFile = "C:\\Users\\User\\eclipse-workspace\\CC-Assig3\\test2.tsv";

		// ======================================
		// Read and write tests
		// ======================================
		TSVRead reader = new TSVRead(sourceFile);
		TSVWrite writer = new TSVWrite(outputFile);

		while (reader.hasNextLine()) {
			record = reader.read();
			if (record != null) {
				writer.write(record);
			}
		}
		reader.close();
		writer.close();

		// ======================================
		// Filter tests
		// ======================================
		System.out.println("================================================");
		TSVFilter filter1 = new TSVFilter.Builder().sourceFile(sourceFile).build();
		TSVPipeline pipe1 = new TSVPipeline(filter1);
		pipe1.doIt();

		System.out.println("================================================");
		TSVFilter filter2 = new TSVFilter.Builder().sourceFile(sourceFile).select("Name", "Ann").build();
		TSVPipeline pipe2 = new TSVPipeline(filter2);
		pipe2.doIt();

		System.out.println("================================================");
		TSVFilter filter3 = new TSVFilter.Builder().sourceFile(sourceFile).select("Age", (byte) 20).build();
		TSVPipeline pipe3 = new TSVPipeline(filter3);
		pipe3.doIt();

		System.out.println("================================================");
		TSVFilter filter4 = new TSVFilter.Builder().sourceFile(sourceFile).select("Name", "Ann")
				.terminate("Age", TerminalObservation.MAX).build();
		TSVPipeline pipe4 = new TSVPipeline(filter4);
		pipe4.doIt();

		System.out.println("================================================");
		TSVFilter filter5 = new TSVFilter.Builder().sourceFile(sourceFile).select("Name", "Ann")
				.terminate("Name", TerminalObservation.COUNT).build();
		TSVPipeline pipe5 = new TSVPipeline(filter5);
		pipe5.doIt();

		System.out.println("================================================");
		TSVFilter filter6 = new TSVFilter.Builder().sourceFile(sourceFile).terminate("Hi", TerminalObservation.COUNT)
				.build();
		TSVPipeline pipe6 = new TSVPipeline(filter6);
		pipe6.doIt();

		System.out.println("================================================");
		TSVFilter filter7 = new TSVFilter.Builder().sourceFile(sourceFile).select("Age", (byte) 20)
				.terminate("Age", TerminalObservation.COUNT).build();
		TSVPipeline pipe7 = new TSVPipeline(filter7);
		pipe7.doIt();
		
		System.out.println("================================================");
		System.out.println(filter7);
	}
}