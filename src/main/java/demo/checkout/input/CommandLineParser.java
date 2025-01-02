package demo.checkout.input;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class CommandLineParser {

	public CommandLineParser() {
		super();
	}
	
	public RentalInputValues parseCommandLine(String[] args) {
		
		RentalInputValues inputValues = new RentalInputValues();	
		
		JCommander jc = new JCommander(inputValues);

		try {
			
			jc.setProgramName("checkout");
			jc.parse(args);
			
			if (inputValues.getHelp()) {
				
				System.out.println ("This is a point-of-sale application to generate a Rental Agreement for tool rentals.\n");
				jc.usage();
				System.exit(1);
			}
			
			// Check for invalid values
			if ((inputValues.getDiscountPercent() < 0) || (inputValues.getDiscountPercent() > 100)) {
				
				jc.usage();
				System.out.println("Invalid input value. The percent discount must be in the range 0 - 100.");
				System.exit(-1);
			}
			
			if (inputValues.getNumberOfDays() < 1) {
				
				jc.usage();
				System.out.println("Invalid input value. The number of days to rent the tool must be at least 1.");
				System.exit(-1);
			}
			
		} catch (ParameterException pe) {
			
			jc.usage();
			
		}
		
		return inputValues;
	}
}
