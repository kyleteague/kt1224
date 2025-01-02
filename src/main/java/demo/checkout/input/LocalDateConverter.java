package demo.checkout.input;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.beust.jcommander.IStringConverter;

import demo.checkout.Checkout;

public class LocalDateConverter implements IStringConverter<LocalDate> {

	@Override
	public LocalDate convert(String value) {
		
		LocalDate date = null; 
		
		if (value.equalsIgnoreCase("today")) {
			
			date = LocalDate.now();
			
		} else {
			
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Checkout.getProperty("DateFormat"));
	        date = LocalDate.parse(value, formatter);
		}
        
		return date;
	}

}
