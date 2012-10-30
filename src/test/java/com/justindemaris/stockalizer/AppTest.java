/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.justindemaris.stockalizer;

import junit.framework.TestCase;

/**
 *
 * @author justin
 */
public class AppTest extends TestCase {
	/**
	 * Instance of our app to test
	 */
	protected App app;
	
	public AppTest(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		app = new App();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInstantiate() {
		assertNotNull(app);
		assertNotNull(app.cluster);
		assertNotNull(app.edgar);
	}
}
