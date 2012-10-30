package com.justindemaris.stockalizer.edgar;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Provides an interface to talk to the SEC Edgar database on company
 * mandatory filings.
 * 
 * @author Justin DeMaris <justin.demaris@gmail.com>
 */
public class Edgar {
	/**
	 * Base URL for all Edgar search queries
	 */
	public String baseUrl = "http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&CIK=";
	
	/**
	 * Builds the URL to fetch a list of filings on a particular stock
	 * 
	 * @param ticker   Symbol (e.g. GOOG)
	 * @param filing   Type (e.g. "10-Q" or "10-K")
	 */
	public String buildSearchUrl(String ticker, String filing) {
		String url = "";
		url = baseUrl + ticker + "&type=" + filing;
		url = url + "&count=100";
		return url;
	}
	
	/**
	 * Generates a list of filings for the given ticker symbol and the given
	 * filing type.
	 * 
	 * @param ticker  E.g. "GOOG"
	 * @param filing  E.g. "10-K"
	 * @throws IOException
	 * @return 
	 */
	public FilingList findFilings(String ticker, String filing) throws IOException {
		String url = buildSearchUrl(ticker, filing);
		Document searchResultPage = pullUrl(url);
		FilingList list = new FilingList(ticker, searchResultPage);
		return list;
	}
	
	/**
	 * Given a URL, pulls it from the web and converts it to a JSoup Document
	 * 
	 * @param url
	 * @throws IOExection
	 * @return JSoup Document representation of the given URL
	 */
	public Document pullUrl(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		return doc;
	}
}
