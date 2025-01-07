package demo.checkout.readfile;

import org.junit.Test;

import demo.checkout.exception.UserCorrectableException;
import demo.checkout.tool.ToolChargeInfo;
import junit.framework.Assert;

public class TestToolChargeDataFileReader {

	@Test
	public void testGetChargeInfoForToolType() {
		
		ToolChargeDataFileReader reader = new ToolChargeDataFileReader("src/test/resources/test_charge.data");
		
		ToolChargeInfo chargeInfo = reader.getChargeInfoForToolType("Ladder");
		Assert.assertEquals(1.99d, chargeInfo.getDailyCharge());
		Assert.assertTrue(chargeInfo.getWeekdayCharge());
		Assert.assertTrue(chargeInfo.getWeekendCharge());
		Assert.assertFalse(chargeInfo.getHolidayCharge());
		
		chargeInfo = reader.getChargeInfoForToolType("Chainsaw");
		Assert.assertEquals(1000000.49d, chargeInfo.getDailyCharge());
		
		try {
			
			chargeInfo = reader.getChargeInfoForToolType("Jackhammer");
			Assert.fail("Reading Jackhammer charge info should have thrown exception.");
		} catch (UserCorrectableException uce) {
			
		}
	}

}
