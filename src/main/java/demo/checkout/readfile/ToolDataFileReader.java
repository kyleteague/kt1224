package demo.checkout.readfile;

import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import demo.checkout.exception.UserCorrectableException;
import demo.checkout.tool.Tool;

public class ToolDataFileReader {
	
	private final String invalidToolCodeMsg = "\nThe tool code you entered: %s, is invalid.\nIf you misspelled the tool " +
	                                          "code, run Checkout again using the correct tool code.\nIf you are certain " +
	                                          "that the tool code you entered is correct, then it needs to be added to " + 
	                                          "the %s file.\n" + DataFileErrorMessages.CONTACT_TECH_SUPPORT;

	private final String invalidAttributeValue = "\nThere is no value present in the %s file for %s for the %s tool code.\n" +
			 "The Checkout application will not run correctly until this value is added to %s.\n" + 
			 DataFileErrorMessages.CONTACT_TECH_SUPPORT;
	
	private String dataFilename;
	
	public ToolDataFileReader(String filename) {
		super();
		dataFilename = filename;
	}
		
	public Tool getToolWithCode(String code) {
		
		if ((code == null) || (code.trim().isEmpty()))
			return null;
		
		Tool tool = null;
				
		try {
			
			Reader in = new FileReader(dataFilename);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
			  .setHeader()
			  .setSkipHeaderRecord(true)
			  .setCommentMarker('#')
			  .setIgnoreSurroundingSpaces(true)
			  .build()
			  .parse(in);
			
			for (CSVRecord aRecord : records) {
				
			    String recordCode = aRecord.get("ToolCode");
			    
			    if (recordCode.equalsIgnoreCase(code)) {
			    	
			    	tool = new Tool();
			    	tool.setCode(recordCode);
			    	
			    	tool.setType(checkValue(aRecord.get("ToolType"), "ToolType", code));
			    	tool.setBrand(checkValue(aRecord.get("Brand"), "Brand", code));
			    }
			}
			
		} catch (UserCorrectableException uce) {
			
			throw uce;
			
		} catch (Exception e) {
			
			throw new UserCorrectableException(DataFileErrorMessages.getFileMovedDeletedCorruptedMessage(dataFilename));
		}
		
		if (tool == null) {
			
			throw new UserCorrectableException(String.format(invalidToolCodeMsg, code, dataFilename));
		}
		
		return  tool;
	}
	
	private String checkValue(String value, String name, String code) {
		
		if ((value == null) || (value.trim().length() < 1)) {

			throw new UserCorrectableException(String.format(invalidAttributeValue, dataFilename, name, code, dataFilename));
		}
		
		return value;		
	}
}
