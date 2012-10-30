package com.justindemaris.stockalizer.edgar;

import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	public FilingList(String ticker, Document searchPage) {
		// Set up our basic, empty filing list
		this();
		
		// Get the table of results
		Elements resultTables = searchPage.getElementsByAttributeValue("summary", "Results");
		
		// Make sure the table exists
		if ( resultTables.size() > 0 ) {
			Element resultTable = resultTables.get(0);

			// Find each of the <tr> tags for each filing
			Elements report_trs = resultTable.getElementsByTag("tr");
			
			// Extract the data points for each provided report
			// intentially skipping the first row as it's the header
			for ( int i = 1; i < report_trs.size(); i++ ) {
				// Get that individual report
				Element tr = report_trs.get(i);
				
				// Create a blank filing
				Filing filing = new Filing();
				
				// Extract the data points
				Elements tds = tr.getElementsByTag("td");
				
				// Skip any row that doesn't have all of the data points we need
				if ( tds.size() < 5 )
					continue;
				
				// Fill in the data points
				filing.type = tds.get(0).text().replace("&nbsp;", "").trim();;
				filing.date = tds.get(3).text().replace("&nbsp;", "").trim();;
				filing.ticker = ticker;
				filing.description = tds.get(2).text().replace("<br />", "\n").replace("&nbsp;", " ").trim();
				
				// Extract the URL to the document list
				Elements links = tds.get(1).getElementsByTag("a");
				Element documentLink = links.get(0);
				filing.url = "http://www.sec.gov" + documentLink.attr("href");
				
				// Add the filing to our list
				filings.add(filing);
			}
			
		}
	}
}
