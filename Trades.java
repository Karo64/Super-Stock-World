package StockWorld;

import java.sql.Timestamp;


public class Trades {

	 
	public int Quantity;
	public double  Price;
	public long Time;
	public types Type;

	

	
	public Trades(long time, int quantity, types Type, double price)
	{
	        this.Quantity = Quantity;
	        this.Price    = Price;
		this.time     = time;
		this.Type     = Type;
	}
	
	public int getQuantity(){
		return Quantity;
		
	}
	
		public double getPrice(){
		return price;
	}
	
	public long getTime(){
		return Time;
	}
	

	public TradeType getTradeType(){
		return Type;
	}
	
	// Time stamp return
	public String getPrintableTime(){
		return new Timestamp(Time).toString();
	}
}
