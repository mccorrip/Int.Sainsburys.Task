package com.sainsburys.integration.scrapermodule.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestJSoupException {

	@Test (expected = JSoupException.class)
	public void testStandard() throws JSoupException {
		try {
			throw new JSoupException("Error");
		} catch (JSoupException e) {
			assertEquals("Error", e.getMessage());
			assertNull(e.getCause());
			throw e;
		}
	}
	
	@Test (expected = JSoupException.class)
	public void testThrowable() throws JSoupException {
		try {
			throw new JSoupException("Error", new Exception());
		} catch (JSoupException e) {
			assertEquals("Error", e.getMessage());
			assertNotNull(e.getCause());
			throw e;
		}
	}
}
