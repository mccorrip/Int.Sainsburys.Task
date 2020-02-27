package com.sainsburys.integration.parsingmodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.sainsburys.integration.parsingmodule.exception.BadHTMLException;
import com.sainsburys.integration.parsingmodule.model.Result;

public class TestJSoupParser {

	final String berrysCherriesCurrantsPathSuccess = "src/test/resources/TestData/HTML/Sainsburys/BerrysCherriesCurrants.html";
	final String berrysCherriesCurrantsPathNoProducts = "src/test/resources/TestData/HTML/Sainsburys/BerrysCherriesCurrantsNoProducts.html";
	final String blueberriesProductPage = "src/test/resources/TestData/HTML/Sainsburys/Blueberries.html";
	final String blueberries400gProductPage = "src/test/resources/TestData/HTML/Sainsburys/Blueberries400g.html";
	final String mixedBerriesNoCalories = "src/test/resources/TestData/HTML/Sainsburys/MixedBerriesNoCalories.html";
	final String cherrysMultiLineDescription = "src/test/resources/TestData/HTML/Sainsburys/CherrysMultiLineDescription.html";

	String tagName = "div.productInfo a";
	String domain = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk";
	String titleQuery = "div.productTitleDescriptionContainer h1";
    String descriptionQuery = "div.productText p";
    String pricePerUnitQuery = "div.pricingAndTrolleyOptions p.pricePerUnit";
    String kcal100gQuery = "table.nutritionTable tr.tableRow0 td, table.nutritionTable tr:nth-child(2) td";
    
	JSoupParser parser = new JSoupParser();
	final String domainSainsburys = "www.sainsburys.co.uk";

	
	@Test
	public void testPositiveURLSearch() throws BadHTMLException, IOException {
		ArrayList<String> actualStrings = parser.parseUrls(TestUtility.readFile(berrysCherriesCurrantsPathSuccess), tagName, domain);
		assertEquals(17, actualStrings.size());
	}
	
	@Test (expected = BadHTMLException.class)
	public void testNegativeNoProducts() throws BadHTMLException, IOException {
		try {
			parser.parseUrls(TestUtility.readFile(berrysCherriesCurrantsPathNoProducts), tagName, domain);
		} catch (BadHTMLException e) {
			assertEquals("No products found under tag: div.productInfo a", e.getMessage());
			throw e;
		}
	}
	
	@Test (expected = BadHTMLException.class)
	public void testNegativeNull() throws BadHTMLException {
		try {
			parser.parseUrls(null, tagName, domain);
		} catch (BadHTMLException e) {
			assertEquals("Null or empty HTML input", e.getMessage());
			throw e;
		}
	}
	
	@Test (expected = BadHTMLException.class)
	public void testNegativeEmpty() throws BadHTMLException, IOException {
		try {
			parser.parseUrls("", tagName, domain);
		} catch (BadHTMLException e) {
			assertEquals("Null or empty HTML input", e.getMessage());
			throw e;
		}
	}
	
	@Test (expected = BadHTMLException.class)
	public void testNegativeNoURLs() throws BadHTMLException, IOException {
		try {
			parser.parseUrls("", tagName, domain);
		} catch (BadHTMLException e) {
			assertEquals("Null or empty HTML input", e.getMessage());
			throw e;
		}
	}
	
	@Test
	public void testParseProductDataBlueberrysAllInfo() throws BadHTMLException, IOException {
		Result result = parser.parseProductData(TestUtility.readFile(blueberriesProductPage), titleQuery, descriptionQuery, pricePerUnitQuery, kcal100gQuery);
		assertEquals("Sainsbury's Blueberries 200g", result.getTitle());
		assertEquals("by Sainsbury's blueberries", result.getDescription());
		assertEquals(new Integer(45), result.getKcal_per_100g());
		assertEquals(new Double(1.75), result.getUnitPrice());
	}
	
	@Test
	public void testParseProductDataBlueberrysUpsell() throws BadHTMLException, IOException {
		Result result = parser.parseProductData(TestUtility.readFile(blueberries400gProductPage), titleQuery, descriptionQuery, pricePerUnitQuery, kcal100gQuery);
		assertEquals("Sainsbury's Blueberries 400g", result.getTitle());
		assertEquals("by Sainsbury's blueberries", result.getDescription());
		assertEquals(new Integer(45), result.getKcal_per_100g());
		assertEquals(new Double(3.25), result.getUnitPrice());
	}
	
	@Test
	public void testParseProductDataMixedBerriesNoCalories() throws BadHTMLException, IOException {
		Result result = parser.parseProductData(TestUtility.readFile(mixedBerriesNoCalories), titleQuery, descriptionQuery, pricePerUnitQuery, kcal100gQuery);
		assertEquals("Sainsbury's Mixed Berries 300g", result.getTitle());
		assertEquals("by Sainsbury's mixed berries", result.getDescription());
		assertEquals(null, result.getKcal_per_100g());
		assertEquals(new Double(3.50), result.getUnitPrice());
	}
	
	@Test
	public void testParseProductDataCherryMultiLineDescription() throws BadHTMLException, IOException {
		Result result = parser.parseProductData(TestUtility.readFile(cherrysMultiLineDescription), titleQuery, descriptionQuery, pricePerUnitQuery, kcal100gQuery);
		assertEquals("Sainsbury's Cherry Punnet 200g", result.getTitle());
		assertEquals("Cherries", result.getDescription());
		assertEquals(new Integer(52), result.getKcal_per_100g());
		assertEquals(new Double(1.50), result.getUnitPrice());
	}
}
