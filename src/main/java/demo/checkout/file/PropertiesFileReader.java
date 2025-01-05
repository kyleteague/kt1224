package demo.checkout.file;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileReader {
	
	private Properties properties;
	private String propertiesFileName;
	
	
	public PropertiesFileReader(String propertiesFileName) {
		super();
		this.propertiesFileName = propertiesFileName;
		properties = new Properties();
	}

	public Properties readProperties() {
		
		try (InputStream input = new FileInputStream(propertiesFileName)) {

			properties.load(input);		
			
        } catch (Exception ex) {     
        	throw new RuntimeException(DataFileErrorMessages.getFileMovedDeletedCorruptedMessage(propertiesFileName));
        }	
		
		return properties;
	}
	
	public String getProperty(String name) {
		
		String value = properties.getProperty(name);
		
		if ((value == null) || (value.trim().length() < 1)) {			
        	throw new RuntimeException(DataFileErrorMessages.getFileMissingOrInvalidValue(propertiesFileName, name));
		}
		
		return value;
	}

}
