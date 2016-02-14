package SuperStockWorld;
import java.util.*;


public abstract class Stocks {

	public String symbol;
	public double Value;
	public double lastDividend;
	public double PriceofMarket;
	private Stack<Trade> tradeHistory;
	
	
	public Stock(String symbol, double lastDividend, double Value){
		this.symbol  = symbol;
		this.lastDividend = lastDividend;
		this.Value = Value;
		
		PriceofMarket = 0.0; 
		
		tradeHistory = new Stack<Trades>();
	}
	

	abstract public double calculateDividendYield();
	
	public void setPrice(double PriceofMarket){
		this.PriceofMarket = PriceofMarket;
	}
	

	public double PERatio(){
	
		 // P/E ratio = price per share / earnings per share
	
	return PriceofMarket/lastDividend;
	}
	

	protected void recordTrade(long time, int quantity, types type, double Price){
		// Add the new trade to the trade history
		tradeHistory.add(new Trade(time, Quantity, type, Price));
	}
	

	public void buy(int Quantity, double Price){
		// Update the market price
		PriceofMArket = Price;
		// and record the BUY trade
		recordTrade(new Date().getTime(), Quantity, TradeType.buy, Price);
	}
	

	public void sell(int quantity, double price){
		// Update the market price
		PriceofMarket = Price;
		// and record the SELL trade
		recordTrade(new Date().getTime(), quantity, TradeTypes.SELL, Price);
	}
	
	
	public void clearTradingHistory(){
		tradeHistory.clear();
	}
	

	public double WeightStockPrice(int minutes){
		double num = 0.0, dec = 0.0;
		
		
		int startTime = new Date().getTime() - minutes*60000;
		
		// Sort and get a reference to the current trade history
		List<Trade> sortedTradeHistory = getHistory();
		
		// For each trade 
		for(Trade t : sortedTradeHistory){
			// within the last (minutes) minutes
			if(startTime < t.getTime()){
				// Add the price of the trade x the quantity to the numerator
				num += t.getPrice() * (double)t.getQuantity();
				// Add the quantity to the denominator 
				dec += (double)t.getQuantity();
			}else{
				// Terminate loop once the trades are no longer 
				// in the interval of interest
				break;
			}
		}
		
		
		return (dec < 1.0) ? 0.0 : (num/dec);
	}
	



	public String getSymbol()
	{
		return symbol;
	}
	

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}
	
	
	public double WeightStockPrice15()
		{
		return WeightStockPrice(15);
	}
	

	public double getValue()
	{
		return Value;
	}
	

	public void setValue(double parValue)
	{
		this.Value = Value;
	}
	

	public double getLastDividend()
	{
		return lastDividend;
	}
	

	public void setLastDividend(double lastDividend)
	{
		this.lastDividend = lastDividend;
	}
	

	public double getPrice()
	{
		return PriceofMarket;
	}
	

	public Stack<Trade> getHistory()
	{
		
		Collections.sort(tradeHistory, new  Comparator<Trade>(){
			@Override
			public int compare(Trade arg0, Trade arg1) {
				return -(int) (arg0.getTime() - arg1.getTime());
			}
		}
		);
		
		return tradeHistory;
	}}
