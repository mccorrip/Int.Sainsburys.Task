package com.sainsburys.integration.parsingmodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.sainsburys.integration.parsingmodule.exception.BadHTMLException;

public class TestSearchPageParser {

	final String berrysCherriesCurrantsPath = "src/test/resources/TestData/HTML/Sainsburys/BerrysCherriesCurrants.html";
	Parser parser = new Parser();
	final String domain = "www.sainsburys.co.uk";

	
	@Test
	public void testPositiveURLSearch() throws BadHTMLException, IOException {
		ArrayList<String> actualStrings = parser.parseUrls(TestUtility.readFile(berrysCherriesCurrantsPath));
		assertEquals(17, actualStrings.size());
	}
	
	@Test (expected = BadHTMLException.class)
	public void testNegativeNull() throws BadHTMLException {
		try {
			parser.parseUrls(null);
		} catch (BadHTMLException e) {
			assertEquals("Null or empty HTML input", e.getMessage());
			throw e;
		}
	}
	
	@Test (expected = BadHTMLException.class)
	public void testNegativeEmpty() throws BadHTMLException, IOException {
		try {
			parser.parseUrls("");
		} catch (BadHTMLException e) {
			assertEquals("Null or empty HTML input", e.getMessage());
			throw e;
		}
	}
	
	@Test (expected = BadHTMLException.class)
	public void testNegativeNoURLs() throws BadHTMLException, IOException {
		try {
			parser.parseUrls("");
		} catch (BadHTMLException e) {
			assertEquals("Null or empty HTML input", e.getMessage());
			throw e;
		}
	}
	
	@Test
	public void testCleanURLsPositiveNoChange() {
		ArrayList<String> uncleanUrls = new ArrayList<String>(Arrays.asList(new String[] {"A","B","C"}));
		ArrayList<String> expectedUrls = new ArrayList<String>(Arrays.asList(new String[] {"A","B","C"}));
		ArrayList<String> actualUrls = parser.cleanUrls(uncleanUrls, domain);
		assertEquals(expectedUrls, actualUrls);
	}
	
	@Test
	public void testCleanURLsNullList() {
		assertNull(parser.cleanUrls(null, domain));
	}
	
	@Test
	public void testCleanURLsNullListAndDomain() {
		assertNull(parser.cleanUrls(null, null));
	}
	
	@Test
	public void testCleanURLsNullDomain() {
		ArrayList<String> uncleanUrls = new ArrayList<String>(Arrays.asList(new String[] {"A","B","C"}));
		ArrayList<String> expectedUrls = new ArrayList<String>(Arrays.asList(new String[] {"A","B","C"}));
		ArrayList<String> actualUrls = parser.cleanUrls(uncleanUrls, null);
		assertEquals(expectedUrls, actualUrls);
	}
	
	@Test
	public void testCleanURLsEmptyArray() {
		ArrayList<String> uncleanUrls = new ArrayList<String>();
		ArrayList<String> expectedUrls = new ArrayList<String>();
		ArrayList<String> actualUrls = parser.cleanUrls(uncleanUrls, "www.sainsburys.co.uk");
		assertEquals(expectedUrls, actualUrls);
	}
	
	@Test
	public void testCleanURLsCleaning() {
		ArrayList<String> uncleanUrls = new ArrayList<String>(Arrays.asList(new String[] {"../../../../../../home/exampleURL1","../../../../../../home/exampleURL2","../../../../../../home/exampleURL3"}));
		ArrayList<String> expectedUrls = new ArrayList<String>(Arrays.asList(new String[] {"www.sainsburys.co.uk/home/exampleURL1","www.sainsburys.co.uk/home/exampleURL2","www.sainsburys.co.uk/home/exampleURL3"}));
		ArrayList<String> actualUrls = parser.cleanUrls(uncleanUrls, "www.sainsburys.co.uk");
		assertEquals(expectedUrls, actualUrls);
	}


}
