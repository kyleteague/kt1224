package demo.checkout.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import demo.checkout.util.DateUtils;

public class DateUtilsTest {

	
	@Test
	public void testIsWeekend() {
		
		// Start at a date that is on a Sunday, then 
		// increment it until every day of the week is tested.
		
		LocalDate aDate = LocalDate.of(2025, 1, 5);
		boolean isWeekend = DateUtils.isWeekend(aDate);
		
		Assert.assertTrue(aDate.getDayOfWeek() == DayOfWeek.SUNDAY);
		Assert.assertTrue(isWeekend);
		
		// Monday thru Friday should return false
		for (int counter = 0; counter < 5; counter++) {
			
			aDate = aDate.plusDays(1);
			isWeekend = DateUtils.isWeekend(aDate);
			
			Assert.assertFalse(isWeekend);
		}
		
		// Saturday should return true
		aDate = aDate.plusDays(1);
		isWeekend = DateUtils.isWeekend(aDate);
		
		Assert.assertTrue(aDate.getDayOfWeek() == DayOfWeek.SATURDAY);
		Assert.assertTrue(isWeekend);
		
	}

	@Test
	public void  testDateIsAHoliday() {
		
		// Each of the following dates are the 1st Monday
		// in September for that year:
		//
		// 9/2/2024    9/4/2023      9/5/2022     9/6/2021    
		// 9/7/2020    9/3/2018      9/1/2014
		
		LocalDate firstMonday = LocalDate.of(2024, 9, 2);
		boolean isHoliday = DateUtils.dateIsAHoliday(firstMonday);
		Assert.assertTrue(isHoliday);
		
		firstMonday = LocalDate.of(2023, 9, 4);
		isHoliday = DateUtils.dateIsAHoliday(firstMonday);
		Assert.assertTrue(isHoliday);
		
		firstMonday = LocalDate.of(2022, 9, 5);
		isHoliday = DateUtils.dateIsAHoliday(firstMonday);
		Assert.assertTrue(isHoliday);
		
		firstMonday = LocalDate.of(2021, 9, 6);
		isHoliday = DateUtils.dateIsAHoliday(firstMonday);
		Assert.assertTrue(isHoliday);
		
		firstMonday = LocalDate.of(2020, 9, 7);
		isHoliday = DateUtils.dateIsAHoliday(firstMonday);
		Assert.assertTrue(isHoliday);
		
		firstMonday = LocalDate.of(2018, 9, 3);
		isHoliday = DateUtils.dateIsAHoliday(firstMonday);
		Assert.assertTrue(isHoliday);
		
		firstMonday = LocalDate.of(2014, 9, 1);
		isHoliday = DateUtils.dateIsAHoliday(firstMonday);
		Assert.assertTrue(isHoliday);
		
		// Test some dates in September that are not the 
		// 1st Monday of the month. Some of these are the
		// 2nd Monday of September.
		
		LocalDate notFirstMonday = LocalDate.of(2024, 9, 1);
		isHoliday = DateUtils.dateIsAHoliday(notFirstMonday);
		Assert.assertFalse(isHoliday);
		
		notFirstMonday = LocalDate.of(2014, 9, 8);
		isHoliday = DateUtils.dateIsAHoliday(notFirstMonday);
		Assert.assertFalse(isHoliday);		
		
		notFirstMonday = LocalDate.of(2018, 9, 4);
		isHoliday = DateUtils.dateIsAHoliday(notFirstMonday);
		Assert.assertFalse(isHoliday);		
		
		notFirstMonday = LocalDate.of(2024, 9, 9);
		isHoliday = DateUtils.dateIsAHoliday(notFirstMonday);
		Assert.assertFalse(isHoliday);		
		
		notFirstMonday = LocalDate.of(2025, 9, 6);
		isHoliday = DateUtils.dateIsAHoliday(notFirstMonday);
		Assert.assertFalse(isHoliday);	
		
		// Test July 4th
		//
		// Real July 4th dates and the day they each fall on:
		// FRIDAY 7/4/2025
		// MONDAY 7/4/2022
		// SUNDAY 7/4/2021
		// SATURDAY 7/4/2020
		
		LocalDate julyFourth = LocalDate.of(2025, 7, 4);
		isHoliday = DateUtils.dateIsAHoliday(julyFourth);	
		Assert.assertTrue(isHoliday);
		
		julyFourth = LocalDate.of(2022, 7, 4);
		isHoliday = DateUtils.dateIsAHoliday(julyFourth);	
		Assert.assertTrue(isHoliday);
		
		// Test July 4th falls on the weekend
		julyFourth = LocalDate.of(2021, 7, 4);
		isHoliday = DateUtils.dateIsAHoliday(julyFourth);	
		Assert.assertFalse(isHoliday);
		
		LocalDate celebratedJulyFourth = LocalDate.of(2021, 7, 5);
		isHoliday = DateUtils.dateIsAHoliday(celebratedJulyFourth);	
		Assert.assertTrue(isHoliday);
		
		julyFourth = LocalDate.of(2020, 7, 4);
		isHoliday = DateUtils.dateIsAHoliday(julyFourth);	
		Assert.assertFalse(isHoliday);
		
		celebratedJulyFourth = LocalDate.of(2020, 7, 3);
		isHoliday = DateUtils.dateIsAHoliday(celebratedJulyFourth);	
		Assert.assertTrue(isHoliday);
	}

}
