package com.sainsburys.integration.parsingmodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.sainsburys.integration.parsingmodule.exception.BadHTMLException;
import com.sainsburys.integration.parsingmodule.model.Result;

public class TestSearchPageParser {

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
    
	Parser parser = new Parser();
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
	public void testCleanURLsPositiveNoChange() {
		ArrayList<String> uncleanUrls = new ArrayList<String>(Arrays.asList(new String[] {"A","B","C"}));
		ArrayList<String> expectedUrls = new ArrayList<String>(Arrays.asList(new String[] {"A","B","C"}));
		ArrayList<String> actualUrls = parser.cleanUrls(uncleanUrls, domainSainsburys);
		assertEquals(expectedUrls, actualUrls);
	}
	
	@Test
	public void testCleanURLsNullList() {
		assertNull(parser.cleanUrls(null, domainSainsburys));
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

	@Test
	public void testRemoveCurrencySymbolsPound() {
		assertEquals("1.20", parser.removeCurrencySymbols("£1.20"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsDollar() {
		assertEquals("1.20", parser.removeCurrencySymbols("$1.20"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsYen() {
		assertEquals("1.20", parser.removeCurrencySymbols("¥1.20"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsEuro() {
		assertEquals("1.20", parser.removeCurrencySymbols("€1.20"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsShekel() {
		assertEquals("1.20", parser.removeCurrencySymbols("₦1.20"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsNiara() {
		assertEquals("1.20", parser.removeCurrencySymbols("₪1.20"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsPoundEnd() {
		assertEquals("1.20", parser.removeCurrencySymbols("1.20£"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsDollarEnd() {
		assertEquals("1.20", parser.removeCurrencySymbols("1.20$"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsYenEnd() {
		assertEquals("1.20", parser.removeCurrencySymbols("1.20¥"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsPoundMultiple() {
		assertEquals("1.20", parser.removeCurrencySymbols("£1.£20£"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsDollarMultiple() {
		assertEquals("1.20", parser.removeCurrencySymbols("$1.$20$"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsYenMultiple() {
		assertEquals("1.20", parser.removeCurrencySymbols("¥1.¥20¥"));
	}
	
	@Test
	public void testRemoveCurrencySymbolsNull() {
		assertEquals(null, parser.removeCurrencySymbols(null));
	}
	
	@Test
	public void testRemoveCurrencySymbolsEmpty() {
		assertEquals("", parser.removeCurrencySymbols(""));
	}
	
	@Test
	public void testRemoveCurrencySymbolsNoneToRemove() {
		assertEquals("1.20", parser.removeCurrencySymbols("1.20"));
	}
	
	@Test
	public void testRemoveKcal() {
		assertEquals(new Integer(150), parser.removeNonNumerical("150kcal"));
	}
	
	@Test
	public void testRemoveKcalMultiple() {
		assertEquals(new Integer(150), parser.removeNonNumerical("kcal150kcal"));
	}
	
	@Test
	public void testRemoveKcalNone() {
		assertEquals(new Integer(150), parser.removeNonNumerical("150"));
	}
	
	@Test
	public void testRemoveKcalEmpty() {
		assertNull(parser.removeNonNumerical(""));
	}
	
	@Test
	public void testRemoveKcalNull() {
		assertNull(parser.removeNonNumerical(null));
	}
	
	@Test
	public void testRemoveKcalRandomLetters() {
		assertNull(parser.removeNonNumerical("kcalrandom"));
	}
}
