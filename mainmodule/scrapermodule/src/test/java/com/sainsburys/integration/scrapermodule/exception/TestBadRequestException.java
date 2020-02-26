package com.sainsburys.integration.scrapermodule.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestBadRequestException {

	@Test (expected = BadRequestException.class)
	public void testStandard() throws BadRequestException {
		try {
			throw new BadRequestException("Error");
		} catch (BadRequestException e) {
			assertEquals("Error", e.getMessage());
			assertNull(e.getCause());
			throw e;
		}
	}
	
	@Test (expected = BadRequestException.class)
	public void testThrowable() throws BadRequestException {
		try {
			throw new BadRequestException("Error", new Exception());
		} catch (BadRequestException e) {
			assertEquals("Error", e.getMessage());
			assertNotNull(e.getCause());
			throw e;
		}
	}
}
