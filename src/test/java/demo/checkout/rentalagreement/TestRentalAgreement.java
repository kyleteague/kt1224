package demo.checkout.rentalagreement;

import java.time.LocalDate;

import org.junit.Test;

import demo.checkout.tool.ToolChargeInfo;
import junit.framework.Assert;

public class TestRentalAgreement {

	
	@Test
	public void testComputeNumberOfChargeDays() {
		
		RentalAgreement rentalAgreement = new RentalAgreement();
		
		ToolChargeInfo chargeInfo = new ToolChargeInfo();
		chargeInfo.setToolType("TEST");
		chargeInfo.setDailyCharge(1.00d);
		chargeInfo.setHolidayCharge(true);
		chargeInfo.setWeekdayCharge(true);
		chargeInfo.setWeekendCharge(true);
		
		// Define a 7 day rental period that has 2 weekend days, 5 weekdays, and 1 holiday
		LocalDate startDate = LocalDate.of(2025, 6, 29);
		LocalDate endDate = LocalDate.of(2025, 7, 6);
		
		int numberOfChargedDays = rentalAgreement.computeNumberOfChargeDays(startDate, endDate, chargeInfo);
		Assert.assertEquals(7, numberOfChargedDays);

		chargeInfo.setWeekendCharge(false);
		numberOfChargedDays = rentalAgreement.computeNumberOfChargeDays(startDate, endDate, chargeInfo);
		Assert.assertEquals(5, numberOfChargedDays);
		
		chargeInfo.setHolidayCharge(false);
		numberOfChargedDays = rentalAgreement.computeNumberOfChargeDays(startDate, endDate, chargeInfo);
		Assert.assertEquals(4, numberOfChargedDays);
		
		chargeInfo.setWeekdayCharge(false);
		chargeInfo.setWeekendCharge(true);
		numberOfChargedDays = rentalAgreement.computeNumberOfChargeDays(startDate, endDate, chargeInfo);
		Assert.assertEquals(2, numberOfChargedDays);
		
		chargeInfo.setWeekendCharge(false);
		numberOfChargedDays = rentalAgreement.computeNumberOfChargeDays(startDate, endDate, chargeInfo);
		Assert.assertEquals(0, numberOfChargedDays);
	}
}
