package demo.checkout.rentalagreement;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import demo.checkout.Checkout;
import demo.checkout.file.ToolChargeDataFileReader;
import demo.checkout.file.ToolDataFileReader;
import demo.checkout.input.RentalInputValues;
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
	private Float preDiscountCharge;
	private Integer discountPercent;
	private Float discountAmount;
	private Float finalCharge;
	
	private boolean isComplete;
	
	public RentalAgreement() {
		super();
		isComplete = false;
	}
		
	public void generate(RentalInputValues inputValues) {

		copyInputValues(inputValues);
		
		getAllToolInfo(inputValues.getToolCode());
		
		computeNumberOfChargeDays(inputValues.getNumberOfDays());
		
		computeAllCharges();
		
		// All of the information needed by the rental agreement 
		// has been read in or computed.
		isComplete = true;
	}
	
	private void getAllToolInfo(String toolCode) {

		tool = new ToolDataFileReader().getToolWithCode(toolCode);		
		chargeInfo = new ToolChargeDataFileReader().getChargeInfoForToolType(tool.getType());
	}
	
	private void copyInputValues(RentalInputValues inputValues) {

		discountPercent = inputValues.getDiscountPercent();
		totalNumberOfDays = inputValues.getNumberOfDays();
		checkoutDate = inputValues.getCheckoutDate();
	}
	
	private void computeAllCharges() {

		preDiscountCharge = CurrencyUtils.roundHalfUpToCents(numberOfChargedDays * chargeInfo.getDailyCharge());
		discountAmount = CurrencyUtils.roundHalfUpToCents(discountPercent/100.0f * preDiscountCharge);	
		finalCharge = preDiscountCharge - discountAmount;
	}
	
	private void computeNumberOfChargeDays(Integer numberOfDays) {
		
		dueDate = checkoutDate.plusDays(numberOfDays);
		numberOfChargedDays = 0;
		LocalDate aRentalDay = checkoutDate.plusDays(1);

		// Loop thru every rental day (as defined in the functional specification)
		// to determine if the customer should be charged for that day, or not.
		do {	
			boolean isWeekend = DateUtils.isWeekend(aRentalDay);

			if (isWeekend) {	
				if (chargeInfo.getWeekendCharge())
					numberOfChargedDays++;
				
			} else {
				// This is a weekday
				
				if (DateUtils.dateIsAHoliday(aRentalDay)) {
					
					// This weekday is also a holiday				
					if (chargeInfo.getHolidayCharge())
						numberOfChargedDays++;
				
				} else {
					
					// This weekday is not a holiday
					if (chargeInfo.getWeekdayCharge()) 
						numberOfChargedDays++;
				}
			}
						
			aRentalDay = aRentalDay.plusDays(1);
			
		} while (!aRentalDay.isAfter(dueDate));		
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
