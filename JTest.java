import java.util.*;
import org.junit.*;
import SuperStockWorld.*;
import static org.junit.Assert.assertEquals;

public class JTest {	
	public static final double MACHINE_TOLERANCE = 1e-08;

	@Test
	public void testTradeConstruct() {
		Trade testTrade = new Trade( new Date().getTime(), 1000, TradeType.BUY, 11);
		
		assertEquals(1000,          testTrade.getQuantity());
		assertEquals(Types.buy, testTrade.getType());
		assertEquals(11,            testTrade.getPrice(),       MACHINE_TOLERANCE);
	}
	

	@Test
	public void testStockConstruct() {
		// Create a new common stock
		CommonStock testStock = new CommonStock("POP", 8, 100);
		
		assertEquals("POP",        testStock.getSymbol());
		assertEquals(8,            testStock.getLastDividend(), MACHINE_TOLERANCE);
		assertEquals(100,          testStock.Value(),     MACHINE_TOLERANCE);
		
		// Create a new preferred stock
		PreferredStock testStock2 = new PreferredStock("POP", 8, 0.02, 100);
		
		assertEquals("POP",        testStock2.getSymbol());
		assertEquals(8,            testStock2.getLastDividend(), MACHINE_TOLERANCE);
		assertEquals(100,          testStock2.getValue(),     MACHINE_TOLERANCE);
		assertEquals(0.02,         testStock2.getFixedDividend(),MACHINE_TOLERANCE);
	}
	
	/* testCommonStockDividendYeild
	 * Tests the calculation for the dividend yield is correct in the case of
	 * the common stock using example given.
	 */
	@Test
	public void testCommonStockDividendYeild() {
		Stock testStock = new generalstock("POP", 8, 100);
		
		// Set fictitious market price
		double marketTestPrice = 100.0;
		testStock.setPrice(marketTestPrice);
		
		assertEquals(8.0/marketTestPrice,  testStock.calculateDividendYield(), MACHINE_TOLERANCE);
	}
	

	@Test
	public void testPreferredStockDividendYeild() {
		Stock testStock = new PreferredStock("POP", 8, 0.02, 100);
		
		// Set fictitious market price
		double marketTestPrice = 100.0;
		testStock.setPrice(marketTestPrice);
		
		assertEquals((0.02*100.0)/marketTestPrice,  testStock.calculateDividendYield(), MACHINE_TOLERANCE);
	}
	

	@Test
	public void testCalculatePERatio(){
		Stock testStock = new generalstock("ALE", 23, 60);
		
		// Set fictitious market price
		double marketTestPrice = 100.0;
		testStock.setPrice(marketTestPrice);
		
		assertEquals(marketTestPrice/23.0,  testStock.calculatePERatio(), MACHINE_TOLERANCE);
	}

	@Test
	public void testRecord() throws InterruptedException{
		Stock testStock = new CommonStock("ALE", 23, 60);
		
		// Set fictitious market price
		double marketTestPrice = 100.0;
		testStock.setPrice(marketTestPrice);
		
		// Store the current computer time
		long closeTime = new Date().getTime();
		
		// Create some fictitious trades to occur
		testStock.buy (123, 101.0);
		Thread.sleep(4); // simulate some time
		testStock.sell(100,  99.0);
		Thread.sleep(4); // simulate some time
		testStock.buy ( 99,  99.9);
		
		// Get the trading history
		Stack<Trade> testHistory = testStock.getHistory();
		
		// Ensure it has the right size firstly
		assertEquals(3,  testHistory.size());
		
		if(testHistory.size() == 3)
		
		{
			int quantities[] = {99, 100, 123};
			int i = 0;
			
			for(Trade t : testHistory){
				
				assertEquals(quantities[i++], t.getQuantity());
				assertEquals((double)closeTime,  (double)t.getTime(), (double)25); // give a 25 ms lee-way (assume a very slow computer)
				
				
				System.out.println("A trade to " + (t.getTradeType()==TradeType.BUY?"buy":"sell") + " " + t.getQuantity() + " shares was placed at " + t.getPrintableTime()); 
			}
		}
	}
	

	@Test
	public void testVolWeightStockPrice(){
	
		class WorkAroundTestStock extends Stock{
			public WorkAroundTestStock(String symbol, double lastDividend,
					double parValue) {
				super(symbol, lastDividend, parValue);
			}

			@Override
			public double calculateDividendYield() {
			
				return 0;
			}
			
			public void buyArbTime(long time, int quantity, double price){
				recordTrade(time, quantity, TradeType.BUY, price);
			}
			
			public void sellArbTime(long time, int quantity, double price){
				recordTrade(time, quantity, TradeType.BUY, price);
			}
		}
		
		WorkAroundTestStock testStock = new WorkAroundTestStock("ALE", 23, 60);
		
		// Set fictitious market price
		double marketTestPrice = 100.0;
		testStock.setPrice(marketTestPrice);
				
		// No trade has been made yet so the volume weighted stock price should be zero 
		assertEquals(0.0, testStock.volWeightStockPrice15(), MACHINE_TOLERANCE);
		
		// Useful to know the current time
		long currentTime = new Date().getTime();
		
		// Create some fictitious trades to occur
		testStock.buy (30, 101.0);
		testStock.sell(60,  99.0);
		testStock.buy (40,  99.9);
		
		double num = 30.0*101.0 + 60.0*99.0 + 40.0*99.9;
		double dec = 30.0       + 60.0      + 40.0     ;
		
		// Print expected volume weighted stock price
		double expectedWeightedStockPrice = num/dec;
		System.out.println("Expected volume weighted stock price is " + expectedWeightedStockPrice);
		
		// Calculate the volume weighted stock price
		assertEquals(expectedWeightedStockPrice, testStock.volWeightStockPrice15(), MACHINE_TOLERANCE);
		
		// Now we add some trades at different times to see the effect
		
		// 1. Insert a trade before the 15 minute mark and ensure the result is unchanged (-20 mins)
		testStock.buyArbTime(currentTime - 20*60000, 50, 102.0);
		assertEquals(expectedWeightedStockPrice, testStock.volWeightStockPrice15(), MACHINE_TOLERANCE);
		
		// 2. Insert a new trade to happen within the past 10 minutes
		testStock.sellArbTime(currentTime - 10*60000, 30, 101.0);
		
		num += 30.0 * 101.0;
		dec += 30.0;
		expectedWeightedStockPrice = num/dec;
		
		assertEquals(expectedWeightedStockPrice, testStock.volWeightStockPrice15(), MACHINE_TOLERANCE);

	}
	
	/* testGBCEAllShareIndex
	 * Tests the GBCE All Share Index calculating 
	 * code is correct.
	 */
	@Test
	public void testAllShareIndex(){
		// Generate an array of stocks
		ArrayList<Stock> stocks = new ArrayList<Stock>();
		// using example provided
		stocks.add(new generalstock("TEA", 0,  100));
		stocks.add(new generalstock("POP", 8,  100));
		stocks.add(new generalstock("ALE", 23, 60));
		stocks.add(new PreferredStock("GIN", 8, .02, 100));
		stocks.add(new generalstock("JOE", 13, 250));
		
		// Set some fictitious market prices for the stocks
		int marketPrices[] = { 101, 99, 89, 20, 44 };
		for(int i=0; i<marketPrices.length; i++){
		
			stocks.get(i).setPrice(marketPrices[i]);
		}
		
		// Calculate expected GBCE All Share Index from above values
		// according to the formula given 
		double expectedGBCE = 1.0;
		for(int i=0; i<marketPrices.length; i++){
			expectedGBCE *= marketPrices[i];
		}
		expectedGBCE = Math.pow(expectedGBCE, 1.0/(double)marketPrices.length);

		// Print what that expected result is
		System.out.println("The expected GBCE All Share Index is " + expectedGBCE);
		
		// And now test for equality
		assertEquals(expectedGBCE, GBCEAllShareIndex.calculateGBCEAllShareIndex(stocks), MACHINE_TOLERANCE);
	}
}
