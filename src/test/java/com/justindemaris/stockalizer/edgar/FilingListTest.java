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
		list = new FilingList("GOOG", doc);
		assertEquals(0, list.filings.size());
	}
	
	public void testInstantiateSimpleDom() {
		String sample = 
			"<html><body><table summary=\"Results\">" +
				"<tr>" +
					"<td>Sample Header</td>" +
					"<td>Col 2</td>" +
					"<td>Col 3</td>" +
					"<td>Col 4</td>" +
					"<td>Col 5</td>" +
				"</tr>" +
				"<tr class=\"blueRow\">" +
					"<td nowrap=\"nowrap\">10-K</td>" +
					"<td nowrap=\"nowrap\"><a href=\"/Archives/edgar/data/1288776/000119312512025336/0001193125-12-025336-index.htm\" id=\"documentsbutton\">&nbsp;Documents</a>&nbsp; <a href=\"/cgi-bin/viewer?action=view&amp;cik=1288776&amp;accession_number=0001193125-12-025336&amp;xbrl_type=v\" id=\"interactiveDataBtn\">&nbsp;Interactive Data</a></td>" +
					"<td class=\"small\" >Sample Description</td>" +
					"<td>2012-01-26</td>" +
					"<td nowrap=\"nowrap\"><a href=\"/cgi-bin/browse-edgar?action=getcompany&amp;filenum=000-50726&amp;owner=include&amp;count=40\">000-50726</a><br>12548435         </td>" +
				"</tr>" +
				"<tr><td>Ignore This</td></tr>" +
		 "</table></body></html>";
		
		Document doc = Jsoup.parse(sample);
		list = new FilingList("GOOG", doc);
		
		assertEquals(1, list.filings.size());
		
		// Make sure it extracted the document properly
		Filing filing = list.filings.get(0);
		assertEquals("10-K", filing.type);
		assertEquals("2012-01-26", filing.date);
		assertEquals("GOOG", filing.ticker);
		assertEquals("http://www.sec.gov/Archives/edgar/data/1288776/000119312512025336/0001193125-12-025336-index.htm", filing.url);
		assertEquals("Sample Description", filing.description);
	}
}
