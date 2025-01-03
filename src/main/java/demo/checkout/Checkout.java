package demo.checkout;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import demo.checkout.error.ErrorMessages;
import demo.checkout.input.CommandLineParser;
import demo.checkout.input.RentalInputValues;
import demo.checkout.rentalagreement.RentalAgreement;

public class Checkout {
	
	private static Properties checkoutProperties;

	//
	// This is the execution starting point for the Checkout command line application.
	//
	public static void main(String[] args) {
		
		try {
			
			// Initialize logging
				
			// Extract the input values from the command line
			CommandLineParser cmdLineParser = new CommandLineParser();
			RentalInputValues inputValues = cmdLineParser.parseCommandLine(args);			
									
			// Load configurable parameters
			readProperties();

			// Generate a Rental Agreement
			RentalAgreement rentalAgreement = new RentalAgreement();
			rentalAgreement.generate(inputValues);
			
			// Print the Rental Agreement
			rentalAgreement.printToConsole();
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			System.exit(-1);
			
		}

	}
	
	public static String getProperty(String name) {
		
		String value = checkoutProperties.getProperty(name);
		
		if ((value == null) || (value.trim().length() < 1)) {
			
			System.out.printf(ErrorMessages.FILE_MISSING_VALUE, "checkout.properties");
        	System.out.println(ErrorMessages.FILE_MUST_BE_REPAIRED);
        	System.out.println(ErrorMessages.CONTACT_TECH_SUPPORT);
        	System.exit(-1);
		}
		
		return value;
	}

	private static void readProperties() {
		
		try (InputStream input = new FileInputStream("../../checkout.properties")) {

			checkoutProperties = new Properties();
			checkoutProperties.load(input);

        } catch (Exception ex) {
            
        	System.out.printf(ErrorMessages.FILE_MOVED_DELETED_CORRUPTED, "checkout.properties");
        	System.out.println(ErrorMessages.FILE_MUST_BE_REPAIRED);
        	System.out.println(ErrorMessages.CONTACT_TECH_SUPPORT);
        	System.exit(-1);
        }	
	}
}
