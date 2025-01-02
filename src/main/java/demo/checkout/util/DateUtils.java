package demo.checkout.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class DateUtils {
	
	public static boolean isWeekend(LocalDate aDate) {
		
		return (aDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || 
				aDate.getDayOfWeek().equals(DayOfWeek.SUNDAY));
	}

	public static boolean dateIsAHoliday(LocalDate aDate) {
		
		// This method only supports the two holidays defined in 
		// the functional specification of the Checkout application.
		
		boolean isHoliday = false;
		
		int dayOfMonth = aDate.getDayOfMonth();

		// Check if this date is July 4th, or the nearest weekday, if the 4th falls on a weekend.
		if ((aDate.getMonth() == Month.JULY) && 
			((dayOfMonth > 2) && (dayOfMonth < 6))) {
				
			LocalDate julyFourth = LocalDate.of(aDate.getYear(), 7, 4);
			
			if ((julyFourth.getDayOfWeek() == DayOfWeek.SATURDAY) && (dayOfMonth == 3)) {
				
				isHoliday = true;
				
			} else if ((julyFourth.getDayOfWeek() == DayOfWeek.SUNDAY) && (dayOfMonth == 5)) {
				
				isHoliday = true;
				
			} else if (dayOfMonth == 4) {
				
				isHoliday = true;		
			}
			
		// Check if this date is Labor Day (first Monday of September)
		} else if ((aDate.getMonth() == Month.SEPTEMBER)  &&
				   (dayOfMonth < 8)) {
			
			LocalDate septFirst = LocalDate.of(aDate.getYear(), 9, 1);
			
			// Start by assuming that Sept. 1st is on a Monday
			int firstMondayDayOfMonth = 1;
			
			// If the 1st is not actually a Monday, then based on what day of the
			// week that the 1st actually falls on, compute how many days must be 
			// added to the 1st to get to the first Monday.
			DayOfWeek dayOfWeek = septFirst.getDayOfWeek();
			if (dayOfWeek != DayOfWeek.MONDAY) {
				firstMondayDayOfMonth += (8 - dayOfWeek.getValue());
			}
			
			if (dayOfMonth == firstMondayDayOfMonth)
				isHoliday = true;
		}
		
		return isHoliday;
	}
}
