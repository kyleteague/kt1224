package demo.checkout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.checkout.exception.UserCorrectableException;
import demo.checkout.input.CommandLineParser;
import demo.checkout.input.RentalInputValues;
import demo.checkout.readfile.PropertiesFileReader;
import demo.checkout.rentalagreement.RentalAgreement;

public class Checkout {
	
	private static PropertiesFileReader propertiesFileReader;
	
	// Initialize application logging
	private static Logger logger = LogManager.getLogger(Checkout.class);
	
	//
	// This is the execution starting point for the Checkout command line application.
	//
	public static void main(String[] args) {
		
		try {
				
			// Read the properties file
			propertiesFileReader = new PropertiesFileReader("checkout.properties");
			propertiesFileReader.readProperties();
							
			// Extract the input values from the command line
			CommandLineParser cmdLineParser = new CommandLineParser();
			RentalInputValues inputValues = cmdLineParser.parseCommandLine(args);			
									
			// Generate a Rental Agreement
			RentalAgreement rentalAgreement = new RentalAgreement();
			rentalAgreement.generate(inputValues);
			
			// Print the Rental Agreement
			rentalAgreement.printToConsole();
			
		} catch (UserCorrectableException uce) {
			
			// Something went wrong that the user can fix.
			// Print an instructive message saying what is wrong 
			// and, in general terms, what they need to do to fix it.
			
			System.out.println(uce.getMessage());
			System.exit(-1);
			
		} catch (Exception e) {
			
			// Something went wrong that that probably requires knowledge and
			// understanding of this code to diagnose and fix.
			// Preserve a stack trace in the log file.
			
			System.out.println("\nA fatal error has occurred that will require technical support to diagnose and correct.");
			System.out.println("Details of this error have been recorded in the application log file.");
			System.out.println("Please contact your technical support team for help.");
			
			logger.fatal(e.getMessage(), e);
			System.exit(-1);
		}
	}
	
	public static String getProperty(String name) {
		
		return propertiesFileReader.getProperty(name);
	}

}
