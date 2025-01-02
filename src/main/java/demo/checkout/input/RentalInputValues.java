package demo.checkout.input;

import java.time.LocalDate;

import com.beust.jcommander.Parameter;

public class RentalInputValues {
	
	@Parameter(names = "-help, -usage", help = true)
	private boolean help;
	
	@Parameter(names = "-code", description = "Tool Code for the tool being rented.", required = true)
	private String toolCode;
	
	@Parameter(names = "-days", description = "The number of days to rent the tool for.", required = true)
	private Integer numberOfDays;
	
	@Parameter(names = "-discount", description = "Percent discount to apply (0 - 100)", required = true)
	private Integer discountPercent;
	
	@Parameter(names = "-date", description = "The checkout date or 'today' to use the current date.",
			   converter = LocalDateConverter.class, required = true)
	private LocalDate checkoutDate;
		
	public String getToolCode() {
		return toolCode;
	}
	
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}
	
	public Integer getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	
	public Integer getDiscountPercent() {
		return discountPercent;
	}
	
	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}
	
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}
	
	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public boolean getHelp() {
		return help;
	}

	public void setHelp(boolean help) {
		this.help = help;
	}
}
