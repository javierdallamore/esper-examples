package org.ef.esper;

import java.util.Date;

public class Tick {
	private String symbol;
	private Double price;
	private Date timeStamp;

	public Tick(String s, double p, long t) {
	    symbol = s;
	    price = p;
	    timeStamp = new Date(t);
	}
	public double getPrice() {return price;}
	public String getSymbol() {return symbol;}
	public Date getTimeStamp() {return timeStamp;}

	public String toString() {
	    return "Price: " + price.toString() + " time: " + timeStamp.toString();
	}
}


