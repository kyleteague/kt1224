package demo.checkout.util;


import org.junit.Test;

import demo.checkout.util.CurrencyUtils;
import junit.framework.Assert;

public class CurrencyUtilsTest {

	
	@Test
	public void testRoundHalfUpToCents() {
		
		// Test some negative numbers
		double valueIn = -2000.348d;
		
		double valueOut = CurrencyUtils.roundHalfUpToCents(valueIn);
		Assert.assertEquals(-2000.35d, valueOut);
		
		valueIn = -99.999d;
		valueOut = CurrencyUtils.roundHalfUpToCents(valueIn);
		Assert.assertEquals(-100.00d, valueOut);
		
		valueIn = -5.5512454535d;
		valueOut = CurrencyUtils.roundHalfUpToCents(valueIn);
		Assert.assertEquals(-5.55d, valueOut);
		
		// Test 0.0
		valueIn = 0.0d;
		valueOut = CurrencyUtils.roundHalfUpToCents(valueIn);
		Assert.assertEquals(0.00d, valueOut);
		
		
		// Test some positive numbers
		valueIn = 0.996d;
		valueOut = CurrencyUtils.roundHalfUpToCents(valueIn);
		Assert.assertEquals(1.00d, valueOut);
		
		valueIn = 1601230.783d;
		valueOut = CurrencyUtils.roundHalfUpToCents(valueIn);
		Assert.assertEquals(1601230.78d, valueOut);
		
	}
}
