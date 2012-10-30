package com.justindemaris.stockalizer.edgar;

import junit.framework.TestCase;

/**
 * Test cases for the Filing class
 *
 * @author Justin DeMaris <justin.demaris@gmail.com>
 */
public class FilingTest extends TestCase {
	Filing filing;
	
	public FilingTest(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		filing = new Filing();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInstantiate() {
		assertNotNull(filing);
	}
	
	public void testFetchFiling() {
		assertNull(filing.fetchFiling());
	}
}
