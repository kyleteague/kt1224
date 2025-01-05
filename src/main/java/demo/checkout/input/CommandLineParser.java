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
			
			jc.setAcceptUnknownOptions(true);
			jc.setProgramName("checkout");
			jc.parse(args);
			
			if ((jc.getUnknownOptions() != null) && (jc.getUnknownOptions().size() > 0)) {
				
				System.out.println ("\nThere are unrecognized options in the command line. Use only the allowed options shown below.\n");
				jc.usage();
				System.exit(-1);
				
			}
			
			if (inputValues.getHelp()) {
				
				System.out.println ("\nThis is a point-of-sale application to generate a Rental Agreement for tool rentals.\n");
				jc.usage();
				System.exit(1);
			}
			
			// Check for invalid values
			if ((inputValues.getDiscountPercent() < 0) || (inputValues.getDiscountPercent() > 100)) {
				
				System.out.println("\nInvalid input value. The percent discount must be in the range 0 - 100.\n");
				jc.usage();
				System.exit(-1);
			}
			
			if (inputValues.getNumberOfDays() < 1) {
				
				System.out.println("\nInvalid input value. The number of days to rent the tool must be at least 1.\n");
				jc.usage();
				System.exit(-1);
			}
			
		} catch (ParameterException pe) {
			
			System.out.println("\n");
			System.out.println(pe.getMessage());
			jc.usage();
			System.exit(-1);
		}
		
		return inputValues;
	}
}
