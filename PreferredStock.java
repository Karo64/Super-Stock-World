package SuperStockWorld;

//Only preferred Stocks

public class PreferredStock extends Stocks{

	double DividentFixed;

	public PreferredStock(String symbol, double lastDividend, double DividentFixed, double Value)
	
	{
		super(symbol, lastDividend, Value);
		this.DividentFixed = DividentFixed;
	}
	
	/* 
	 * Calculates the stock dividend yield based on the formula:
	 *   (Fixed Dividend x Par Value)/Market Price 
	 */
	public double DividendYield(){
		return (DividentFixed*Value)/PriceofMarket;
	}
	
	public double getFixedDividend(){
		return DividentFixed;
	}
}
