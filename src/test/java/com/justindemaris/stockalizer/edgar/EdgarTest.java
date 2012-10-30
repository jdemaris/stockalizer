/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.justindemaris.stockalizer.edgar;

import java.io.IOException;
import junit.framework.TestCase;
import org.jsoup.nodes.Document;

/**
 *
 * @author justin
 */
public class EdgarTest extends TestCase {
	/**
	 * Instance of our Edgar wrapper to test
	 */
	protected Edgar edgar;
	
	public EdgarTest(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		edgar = new Edgar();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInstantiate() {
		assertNotNull(edgar);
	}
	
	public void testBuildUrl() {
		String ticker = "GOOG";
		String filing = "10-K";
		String url = edgar.buildSearchUrl(ticker, filing);
		
		assertEquals(
			"http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&CIK=GOOG&type=10-K",
			url
		);
	}
	
	public void testRequestValidUrl() {
		String url = "http://localhost";
		Document result;
		try {
			result = edgar.pullUrl(url);
		} catch ( IOException e ) {
			result = null;
		}
		assertNotNull(result);
	}
	
	public void testRequestInvalidUrl() {
		String url = "http://localhost/bad-url.xml";
		Document result;
		try {
			result = edgar.pullUrl(url);
		} catch ( IOException e ) {
			result = null;
		}
		assertNull(result);
	}
}
