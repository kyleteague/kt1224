package demo.checkout.readfile;

public class DataFileErrorMessages {
	
	public static String FILE_MISSING_OR_INVALID_VALUE = "\nThe value for %s in the %s file is either missing or invalid.\n";
	public static String FILE_MOVED_DELETED_CORRUPTED = "\nThe %s file appears to have been moved, deleted, or corrupted.\n";
	public static String FILE_MUST_BE_REPAIRED = "The Checkout application will not run correctly until this file is repaired.\n";
	public static String CONTACT_TECH_SUPPORT = "Please contact your technical support team if you need help modifying this file.";

	
	public static String getFileMovedDeletedCorruptedMessage(String filename) {
			
		StringBuilder builder = new StringBuilder();
		
		builder.append(String.format(FILE_MOVED_DELETED_CORRUPTED, filename));
		builder.append(FILE_MUST_BE_REPAIRED);
		builder.append(CONTACT_TECH_SUPPORT);
		
		return builder.toString();
	}
	
	public static String getFileMissingOrInvalidValue(String filename, String missingValue) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(String.format(FILE_MISSING_OR_INVALID_VALUE, missingValue, filename));
		builder.append(FILE_MUST_BE_REPAIRED);
		builder.append(CONTACT_TECH_SUPPORT);
		
		return builder.toString();
	}

}
