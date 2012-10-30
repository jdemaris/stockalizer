package com.justindemaris.stockalizer.edgar;

import junit.framework.TestCase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Tests the FilingList functionality
 * 
 * @author Justin DeMaris <justin.demaris@gmail.com>
 */
public class FilingListTest extends TestCase {

	FilingList list;
	
	public FilingListTest(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		list = new FilingList();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testInstantiate() {
		assertNotNull(list);
		assertEquals(0, list.filings.size());
	}
	
	public void testInstantiateEmptyDom() {
		Document doc = Jsoup.parse("<html></html>");
		list = new FilingList(doc);
		assertEquals(0, list.filings.size());
	}
}
