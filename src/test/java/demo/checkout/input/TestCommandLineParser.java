package demo.checkout.input;

import java.time.LocalDate;

import org.junit.Test;

import junit.framework.Assert;

public class TestCommandLineParser {

	
	@Test
	public void testParseCommandLine() {
		
		RentalInputValues inputValues;
		CommandLineParser cmdLineParser = new CommandLineParser();
		
		String[] args = { "--code", "LADW", "--days", "5", "--discount", "10", "--date", "03/15/2025" };
		
		inputValues = cmdLineParser.parseCommandLine(args);			

		Assert.assertEquals("LADW", inputValues.getToolCode());
		Assert.assertEquals(5, inputValues.getNumberOfDays().intValue());
		Assert.assertEquals(10, inputValues.getDiscountPercent().intValue());
		Assert.assertEquals(LocalDate.of(2025, 3, 15), inputValues.getCheckoutDate());
	}
}
