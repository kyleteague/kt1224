package demo.checkout.input;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.beust.jcommander.IStringConverter;

import demo.checkout.Checkout;

public class LocalDateConverter implements IStringConverter<LocalDate> {

	@Override
	public LocalDate convert(String value) {
		
		LocalDate date = null; 
		
		if (value.equalsIgnoreCase("today")) {
			
			date = LocalDate.now();
			
		} else {
			
			try {
				
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Checkout.getProperty("DateFormat"));
		        date = LocalDate.parse(value, formatter);
		        
			} catch (DateTimeParseException e) {
				
				System.out.printf("\nThe rental checkout date must be 'today' or in the format: %s\n", Checkout.getProperty("DateFormat"));
				System.exit(-1);
			}
		}
        
		return date;
	}

}
