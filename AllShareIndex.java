package SuperStockWorld;

import java.util.List;

// GBCEAllShareIndex
public class AllShareIndex {


	static public double computationAllShareIndex(List<Stock> stocks){
		double pM = 1.0;
	 
		for(Stocks s : stocks)
		{
			pM *= s.getPrice();
		}
	
		return Math.pow(pM,1/(double)stocks.size());
	}
	
}
