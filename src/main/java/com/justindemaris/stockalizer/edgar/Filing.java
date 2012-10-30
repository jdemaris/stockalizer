package com.justindemaris.stockalizer.edgar;

/**
 * A single Edgar filing representation. The actual filing needs to extend this
 * and provide real financial data since this simply describes where the filing
 * is located and when it is from.
 * 
 * @author Justin DeMaris <justin.demaris@gmail.com>
 */
public class Filing {
	/**
	 * Date the filing was made
	 */
	String date;
	
	/**
	 * Name of the filing
	 */
	String name;
	
	/**
	 * Ticker symbol the filing is for
	 */
	String ticker;
	
	/**
	 * URL that the filing is stored at
	 */
	String url;
	
	/**
	 * Hits up the web server and pulls this filing
	 */
	public Filing fetchFiling() {
		return null;
	}
}
