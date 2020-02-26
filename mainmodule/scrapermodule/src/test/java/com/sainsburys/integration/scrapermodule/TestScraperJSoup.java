package com.sainsburys.integration.scrapermodule;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.sainsburys.integration.scrapermodule.exception.BadRequestException;
import com.sainsburys.integration.scrapermodule.exception.JSoupException;
import com.sainsburys.integration.scrapermodule.exception.TestUtility;

public class TestScraperJSoup {
	
	JSoupScraper scraper = new JSoupScraper();
	TestUtility testUtility = new TestUtility();
	String berrysCherriesCurrantsPath = "src/test/resources/TestData/HTML/Sainsburys/BerrysCherriesCurrants.html";
	String validURL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
	String invalidURL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants60390000.html";
	
	@Test
	public void testScraperPositive() throws Exception {
		assertEqualsIgnoreSpaces(TestUtility.readFile(berrysCherriesCurrantsPath), scraper.scrape(validURL));	
	}
	
	@Test (expected = BadRequestException.class)
	public void testScraperNullURL() throws Exception {
		try {
			scraper.scrape(null);
		} catch (BadRequestException e) {
			assertEquals("Null or empty URL provided", e.getMessage());
			throw e;
		}
	}
	
	@Test (expected = BadRequestException.class)
	public void testScraperEmpty() throws Exception {
		try {
			scraper.scrape("");
		} catch (BadRequestException e) {
			assertEquals("Null or empty URL provided", e.getMessage());
			throw e;
		}
	}
	
	@Test (expected = JSoupException.class)
	public void testScraperIncorrect() throws Exception {
		try {
			scraper.scrape(invalidURL);
		} catch (JSoupException e) {
			assertEquals("404 Endpoint Not Found", e.getMessage());
			throw e;
		}
	}
	
	

	private void assertEqualsIgnoreSpaces(String expected, String actual) {
		expected = TestUtility.removeWhiteSpaces(expected);
		actual = TestUtility.removeWhiteSpaces(actual);
		assertEquals(expected, actual);
	}
}
