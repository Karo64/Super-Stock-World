package SuperStockWorld;

import java.util.List;

// GBCEAllShareIndex
public class AllShareIndex {

	/* calculateGBCEAllShareIndex
	 * Returns the GBCE All Share Index as calculated using the geometric
	 * mean for all stocks (listed in the stocks parameter). 
	 */
	static public double computationAllShareIndex(List<Stock> stocks){
		double pM = 1.0;
		// Firstly multiply all prices together
		for(Stocks s : stocks)
		{
			pM *= s.getPrice();
		}
		// then take that value pM and return pM^(1/n)
		// where n is the number of stocks in the list
		return Math.pow(pM,1/(double)stocks.size());
	}
	
}
