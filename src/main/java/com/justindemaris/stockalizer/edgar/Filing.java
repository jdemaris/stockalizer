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
	public String date;
	
	/**
	 * Description of the filing
	 */
	public String description;
	
	/**
	 * Ticker symbol the filing is for
	 */
	public String ticker;
	
	/**
	 * E.g. "10-K"
	 */
	public String type;
	
	/**
	 * URL that the filing is stored at
	 */
	public String url;
	
	/**
	 * Hits up the web server and pulls this filing
	 */
	public Filing fetchFiling() {
		return null;
	}
}
