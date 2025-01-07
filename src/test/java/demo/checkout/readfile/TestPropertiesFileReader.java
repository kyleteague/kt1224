package demo.checkout.readfile;

import org.junit.Test;

import demo.checkout.exception.UserCorrectableException;
import junit.framework.Assert;

public class TestPropertiesFileReader {

	@Test
	public void testReadingAndGettingProperties() {
		
		PropertiesFileReader reader = new PropertiesFileReader("src/test/resources/test.properties");
				
		reader.readProperties();
		
		String value = reader.getProperty("TEST1");
		Assert.assertEquals("VAL1", value);
		
		value = reader.getProperty("TEST2");
		Assert.assertEquals("VAL2", value);
	
		value = reader.getProperty("TEST3");
		Assert.assertEquals("VAL3", value);
		
		try {
			value = reader.getProperty("TEST4");
			Assert.fail("Reading TEST4 property should have thrown exception.");
		} catch (UserCorrectableException uce) {
			
		}
	}
}
