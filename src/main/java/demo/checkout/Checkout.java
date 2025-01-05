package demo.checkout;

import demo.checkout.file.PropertiesFileReader;
import demo.checkout.input.CommandLineParser;
import demo.checkout.input.RentalInputValues;
import demo.checkout.rentalagreement.RentalAgreement;

public class Checkout {
	
	private static PropertiesFileReader propertiesFileReader;
	
	//
	// This is the execution starting point for the Checkout command line application.
	//
	public static void main(String[] args) {
		
		try {
				
			// Read the properties file
			propertiesFileReader = new PropertiesFileReader("checkout.properties");
			propertiesFileReader.readProperties();
			
			// Initialize logging
				
			// Extract the input values from the command line
			CommandLineParser cmdLineParser = new CommandLineParser();
			RentalInputValues inputValues = cmdLineParser.parseCommandLine(args);			
									
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
		
		String value = null;
		try {
			value = propertiesFileReader.getProperty(name);
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			System.exit(-1);
		}	
		
		return value;
	}
}
