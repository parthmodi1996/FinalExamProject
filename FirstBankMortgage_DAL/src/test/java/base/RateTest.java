package base;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RateTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testRate() {
		assertTrue("Rate test", RateDAL.getRate(700)==4.0);
	}
	
	@Test
	public void testMortgage() {
		double mortgage = RateDAL.CalculateMortgage(300000, 700, 30);
		System.out.println("Mortgage is $" + mortgage);
		assertTrue("Mortgage test", mortgage == 1432.25);
	}


}
