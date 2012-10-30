package com.justindemaris.stockalizer.edgar;

import java.util.ArrayList;
import org.jsoup.nodes.Document;

/**
 * List of URLs and names to SEC filings pulled from Edgar
 * 
 * @author Justin DeMaris <justin.demaris@gmail.com>
 */
public class FilingList {
	/**
	 * List of Filing objects that we can fetch individually
	 */
	public ArrayList<Filing> filings;
	
	/**
	 * Default to an empty list
	 */
	public FilingList() {
		filings = new ArrayList<Filing>();
	}
	
	/**
	 * Given an Edgar result page, extracts the filings and stores them
	 * locally.
	 * 
	 * @param searchPage DOM for the search result page so we can parse it
	 */
	public FilingList(Document searchPage) {
		// Set up our basic, empty filing list
		this();
	}
}
