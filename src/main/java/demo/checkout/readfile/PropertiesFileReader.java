package demo.checkout.readfile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import demo.checkout.exception.UserCorrectableException;

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
        	throw new UserCorrectableException(DataFileErrorMessages.getFileMovedDeletedCorruptedMessage(propertiesFileName));
        }	
		
		return properties;
	}
	
	public String getProperty(String name) {
		
		String value = properties.getProperty(name);
		
		if ((value == null) || (value.trim().length() < 1)) {			
        	throw new UserCorrectableException(DataFileErrorMessages.getFileMissingOrInvalidValue(propertiesFileName, name));
		}
		
		return value;
	}

}
