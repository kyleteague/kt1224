package demo.checkout.file;

import java.io.FileReader;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import demo.checkout.Checkout;
import demo.checkout.tool.Tool;

public class ToolDataFileReader {
	
	private final String invalidToolCodeMsg = "\nThe tool code you entered: %s, is invalid.\nIf you misspelled the tool " +
	                                          "code, run Checkout again using the correct tool code.\nIf you are certain " +
	                                          "that the tool code you entered is correct, then it needs to be added to " + 
	                                          "the %s file.\n" + DataFileErrorMessages.CONTACT_TECH_SUPPORT;
	
	public Tool getToolWithCode(String code) {
		
		if ((code == null) || (code.trim().isEmpty()))
			return null;
		
		Tool tool = null;
		
		String dataFilename = Checkout.getProperty("ToolsDataFile");
		
		try {
			
			Reader in = new FileReader(dataFilename);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
			  .setHeader()
			  .setSkipHeaderRecord(true)
			  .build()
			  .parse(in);
			
			for (CSVRecord aRecord : records) {
				
			    String recordCode = aRecord.get("ToolCode");
			    
			    if (recordCode.equalsIgnoreCase(code)) {
			    	tool = new Tool();
			    	tool.setCode(recordCode);
			    	tool.setType(aRecord.get("ToolType"));
			    	tool.setBrand(aRecord.get("Brand"));
			    }
			}
			
		} catch (Exception e) {
			
			throw new RuntimeException(DataFileErrorMessages.getFileMovedDeletedCorruptedMessage(dataFilename));
		}
		
		if (tool == null) {
			
			throw new RuntimeException(String.format(invalidToolCodeMsg, code, dataFilename));
		}
		
		return  tool;
	}
}
