package demo.checkout.readfile;

import java.io.FileReader;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import demo.checkout.exception.UserCorrectableException;
import demo.checkout.exception.YesNoValueRuntimeException;
import demo.checkout.tool.ToolChargeInfo;

public class ToolChargeDataFileReader {
	
	private String dataFilename;
	private String toolType;
	
	private final String noChargeInfoStr = "\nThere is no charge information in the %s file for the %s tool type.\n" + 
										   "The %s file is either not up to date, or it has been corrupted.\n" + 
										   DataFileErrorMessages.CONTACT_TECH_SUPPORT;
	
	private final String invalidYesNoValue = "\nThere either is no value for %s in the %s file for %s tool type, or " + 
			                                 "the value is invalid.\nThis value must be present in the %s file, and " +
			                                 "it must be either 'Yes' or 'No'.\n" + DataFileErrorMessages.CONTACT_TECH_SUPPORT;
	
	public ToolChargeDataFileReader(String filename) {
		super();
		dataFilename = filename;
	}

	public ToolChargeInfo getChargeInfoForToolType(String type) {
		
		if ((type == null) || (type.trim().length() < 1))
			return null;
		
		ToolChargeInfo toolChargeInfo = null;
		toolType = type;
		
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
				
			    String recordType = aRecord.get("ToolType");
			    
			    if (toolType.equalsIgnoreCase(recordType)) {
			    	
			    	toolChargeInfo = new ToolChargeInfo();
			    	toolChargeInfo.setToolType(recordType);
			    	toolChargeInfo.setDailyCharge(Double.parseDouble(aRecord.get("DailyCharge")));
			    	toolChargeInfo.setWeekdayCharge(getChargeValueForTypeOfDay("WeekdayCharge", aRecord));
			    	toolChargeInfo.setWeekendCharge(getChargeValueForTypeOfDay("WeekendCharge", aRecord));
			    	toolChargeInfo.setHolidayCharge(getChargeValueForTypeOfDay("HolidayCharge", aRecord));
			    }
			}
			
		} catch (NumberFormatException nfe) {

			throw new UserCorrectableException(DataFileErrorMessages.getFileMissingOrInvalidValue(dataFilename, "DailyCharge"));

		} catch (YesNoValueRuntimeException ynve) {
			
			throw ynve;
			
		} catch (Exception e) {
			
			throw new UserCorrectableException(DataFileErrorMessages.getFileMovedDeletedCorruptedMessage(dataFilename));
		}
		
		if (toolChargeInfo == null) {
			
        	throw new UserCorrectableException(String.format(noChargeInfoStr, dataFilename, toolType, dataFilename));	
		}
		
		return  toolChargeInfo;
	}
	
	private boolean getChargeValueForTypeOfDay(String dayType, CSVRecord aRecord) {
		
		String yesNoStr = aRecord.get(dayType);
		
		if ("yes".equalsIgnoreCase(yesNoStr))
			return true;
		
		else if ("no".equalsIgnoreCase(yesNoStr))
			return false;
		
		// Any string, including null, that is not 'yes' or 'no' is invalid
    	throw new YesNoValueRuntimeException(String.format(invalidYesNoValue, dayType, dataFilename, toolType, dataFilename));	
	}

}
