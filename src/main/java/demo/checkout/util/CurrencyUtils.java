package demo.checkout.util;

public class CurrencyUtils {
	
	public static double roundHalfUpToCents(double valueToRound) {
		
		double roundedNumber = Math.round(valueToRound * 100.0d) / 100.0d;
		return roundedNumber;
	}

}
