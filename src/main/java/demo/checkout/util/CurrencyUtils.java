package demo.checkout.util;

public class CurrencyUtils {
	
	public static float roundHalfUpToCents(float valueToRound) {
		
		float roundedNumber = Math.round(valueToRound * 100.0f) / 100.0f;
		return roundedNumber;
	}

}
