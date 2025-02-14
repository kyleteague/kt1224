package demo.checkout.rentalagreement;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import demo.checkout.Checkout;
import demo.checkout.input.RentalInputValues;
import demo.checkout.readfile.ToolChargeDataFileReader;
import demo.checkout.readfile.ToolDataFileReader;
import demo.checkout.tool.Tool;
import demo.checkout.tool.ToolChargeInfo;
import demo.checkout.util.CurrencyUtils;
import demo.checkout.util.DateUtils;

public class RentalAgreement {
	
	private Tool tool;
	private ToolChargeInfo chargeInfo;
	
	private Integer totalNumberOfDays;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private Integer numberOfChargedDays;
	private Double preDiscountCharge;
	private Integer discountPercent;
	private Double discountAmount;
	private Double finalCharge;
	
	private boolean isComplete;
	
	public RentalAgreement() {
		super();
		isComplete = false;
	}
		
	public void generate(RentalInputValues inputValues) {

		processInputValues(inputValues);
		
		getAllToolInfo(inputValues.getToolCode());
		
		numberOfChargedDays = computeNumberOfChargeDays(checkoutDate, dueDate, chargeInfo);
		
		computeAllCharges();
		
		// All of the information needed by the rental agreement 
		// has been read in or computed.
		isComplete = true;
	}
	
	private void getAllToolInfo(String toolCode) {

		tool = new ToolDataFileReader(Checkout.getProperty("ToolsDataFile")).getToolWithCode(toolCode);		
		chargeInfo = new ToolChargeDataFileReader(Checkout.getProperty("ChargeInfoDataFile")).getChargeInfoForToolType(tool.getType());
	}
	
	private void processInputValues(RentalInputValues inputValues) {

		discountPercent = inputValues.getDiscountPercent();
		totalNumberOfDays = inputValues.getNumberOfDays();
		checkoutDate = inputValues.getCheckoutDate();
		dueDate = checkoutDate.plusDays(totalNumberOfDays);
	}
	
	private void computeAllCharges() {

		preDiscountCharge = CurrencyUtils.roundHalfUpToCents(numberOfChargedDays * chargeInfo.getDailyCharge());
		discountAmount = CurrencyUtils.roundHalfUpToCents(discountPercent/100.0f * preDiscountCharge);	
		finalCharge = preDiscountCharge - discountAmount;
	}
	
	int computeNumberOfChargeDays(LocalDate rentalStartDate, LocalDate rentalEndDate, ToolChargeInfo toolChargeInfo) {		
		
		int numDaysCharged = 0;
		LocalDate aRentalDay = rentalStartDate.plusDays(1);

		// Loop thru every rental day (as defined in the functional specification)
		// to determine if the customer should be charged for that day, or not.
		do {	
			
			boolean isWeekend = DateUtils.isWeekend(aRentalDay);

			if (isWeekend) {	
				if (toolChargeInfo.getWeekendCharge())
					numDaysCharged++;
				
			} else {
				// This is a weekday
				
				if (DateUtils.dateIsAHoliday(aRentalDay)) {
					
					// This weekday is also a holiday				
					if (toolChargeInfo.getHolidayCharge())
						numDaysCharged++;
				
				} else {
					
					// This weekday is not a holiday
					if (toolChargeInfo.getWeekdayCharge()) 
						numDaysCharged++;
				}
			}
						
			aRentalDay = aRentalDay.plusDays(1);
			
		} while (!aRentalDay.isAfter(rentalEndDate));	
		
		return numDaysCharged;
	}
	
	public void printToConsole() {
				
		// Do not display the rental agreement if it is incomplete.
		if (!isComplete) {
			System.out.println("\nThe Rental Agreement is incomplete. There is still at least 1 value that has not been determined.");
			return;
		}

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Checkout.getProperty("DateFormat"));
		NumberFormat chargeFormatter = NumberFormat.getCurrencyInstance(Locale.US);
				
		StringBuilder builder = new StringBuilder("\n\nRENTAL AGREEMENT");

		builder.append("\nTool Code: ").append(tool.getCode());
		builder.append("\nTool Type: ").append(tool.getType());
		builder.append("\nTool Brand: ").append(tool.getBrand());
		
		builder.append("\n\nRental Days: ").append(totalNumberOfDays);
		builder.append("\nCheck Out Date: ").append(checkoutDate.format(dateFormatter));
		builder.append("\nDue Date: ").append(dueDate.format(dateFormatter));
		
		builder.append("\n\nDaily Rental Charge: ").append(chargeFormatter.format(chargeInfo.getDailyCharge()));
		builder.append("\nCharge Days: ").append(numberOfChargedDays);
		builder.append("\nPre-Discount Charge: ").append(chargeFormatter.format(preDiscountCharge));
		builder.append("\nDiscount Percent: ").append(discountPercent).append("%");
		builder.append("\nDiscount Amount: ").append(chargeFormatter.format(discountAmount));
		builder.append("\nFinal Charge: ").append(chargeFormatter.format(finalCharge));
		builder.append("\n\n");

		System.out.println(builder.toString());
	}
	
}
