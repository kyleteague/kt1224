package demo.checkout.tool;

import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import demo.checkout.Checkout;
import demo.checkout.error.ErrorMessages;

public class ToolChargeInfoFabricator {
	
	private String dataFilename;
	private String toolType;

	public ToolChargeInfo getChargeInfoForToolType(String type) {
		
		if ((type == null) || (type.trim().length() < 1))
			return null;
		
		ToolChargeInfo toolChargeInfo = null;
		dataFilename = Checkout.getProperty("ChargeInfoDataFile");
		toolType = type;
		
		try {
				
			Reader in = new FileReader(dataFilename);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
			  .setHeader()
			  .setSkipHeaderRecord(true)
			  .build()
			  .parse(in);
			
			for (CSVRecord aRecord : records) {
				
			    String recordType = aRecord.get("ToolType");
			    
			    if (recordType.equalsIgnoreCase(toolType)) {
			    	
			    	toolChargeInfo = new ToolChargeInfo();
			    	toolChargeInfo.setToolType(recordType);
			    	toolChargeInfo.setDailyCharge(Float.parseFloat(aRecord.get("DailyCharge")));
			    	toolChargeInfo.setWeekdayCharge(getChargeValueForTypeOfDay("WeekdayCharge", aRecord));
			    	toolChargeInfo.setWeekendCharge(getChargeValueForTypeOfDay("WeekendCharge", aRecord));
			    	toolChargeInfo.setHolidayCharge(getChargeValueForTypeOfDay("HolidayCharge", aRecord));
			    }
			}
			
		} catch (NumberFormatException nfe) {

        	System.out.printf("The daily charge value for %s is either missing or invalid in the %s file.\n", toolType, dataFilename);
        	System.out.println(ErrorMessages.CONTACT_TECH_SUPPORT);
        	System.exit(-1);

		} catch (Exception e) {
			
        	System.out.printf(ErrorMessages.FILE_MOVED_DELETED_CORRUPTED, dataFilename);
        	System.out.println(ErrorMessages.FILE_MUST_BE_REPAIRED);
        	System.out.println(ErrorMessages.CONTACT_TECH_SUPPORT);
        	System.exit(-1);
		}
		
		if (toolChargeInfo == null) {
			
        	System.out.printf("There is no charge information in the %s file for the %s tool type.\n", dataFilename, toolType);
        	System.out.printf("The %s file is either not up to date, or it has been corrupted.\n", dataFilename);
        	System.out.println(ErrorMessages.CONTACT_TECH_SUPPORT);
        	System.exit(-1);		
		}
		
		return  toolChargeInfo;
	}
	
	private boolean getChargeValueForTypeOfDay(String dayType, CSVRecord aRecord) {
		
		String yesNoStr = aRecord.get(dayType);
		
		if ("yes".equalsIgnoreCase(yesNoStr))
			return true;
		
		else if ("no".equalsIgnoreCase(yesNoStr))
			return false;
		
		// Any string including null, that is not 'yes' or 'no' is invalid
    	System.out.printf("There either is no value for %s in the %s file for %s tool type, or the value is invalid.\n", dayType, dataFilename, toolType);
    	System.out.printf("This value must be present in the %s file, and it must be either 'Yes' or 'No'.\n", dataFilename);
    	System.out.println(ErrorMessages.CONTACT_TECH_SUPPORT);
    	System.exit(-1);
		
		return false;
	}

}
