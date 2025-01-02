package demo.checkout.tool;

import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import demo.checkout.Checkout;
import demo.checkout.error.ErrorMessages;

public class ToolFabricator {
	
	public Tool getToolWithCode(String code) {
		
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
			
        	System.out.printf(ErrorMessages.FILE_MOVED_DELETED_CORRUPTED, dataFilename);
        	System.out.println(ErrorMessages.FILE_MUST_BE_REPAIRED);
        	System.out.println(ErrorMessages.CONTACT_TECH_SUPPORT);
        	System.exit(-1);
		}
		
		if (tool == null) {
			
        	System.out.printf("The tool code you entered: %s is invalid.\n", code);
        	System.out.println("If you misspelled the tool code, run Checkout again using the correct tool code.");
        	System.out.printf("If you are certain that the tool code you entered is correct, then it needs to be added to the %s file.\n", dataFilename);
        	System.out.println(ErrorMessages.CONTACT_TECH_SUPPORT);
        	System.exit(-1);
			
		}
		
		return  tool;
	}
}
