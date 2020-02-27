package com.sainsburys.integration.runnermodule;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sainsburys.integration.parsingmodule.JSoupParser;
import com.sainsburys.integration.runnermodule.model.Response;
import com.sainsburys.integration.scrapermodule.JSoupScraper;

public class TestRunner {

	Runner runner = new Runner();
	
	private String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
	private String URLTAGNAME = "div.productInfo a";
	private String DOMAIN = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk";
	private String TITLEQUERY = "div.productTitleDescriptionContainer h1";
	private String DESCRIPTIONQUERY = "div.productText p";
	private String PRICEPERUNITQUERY = "div.pricingAndTrolleyOptions p.pricePerUnit";
	private String KCAL100GQUERY = "table.nutritionTable tr.tableRow0 td, table.nutritionTable tr:nth-child(2) td";
	private Double VAT = 0.2;
	
	@Test
	public void testEndToEnd() throws Exception {
		Response response = runner.init(new JSoupScraper(), new JSoupParser(), createConfig());
		assertEquals(new Double(39.5), new Double(response.getTotal().getGross()));
		assertEquals(new Double(6.583333333333335), new Double(response.getTotal().getVat()));
		ObjectMapper Obj = new ObjectMapper();
		String expected = readFile("src/test/resources/Response", StandardCharsets.UTF_8);
		String actual = Obj.writerWithDefaultPrettyPrinter().writeValueAsString(response);
		assertEquals(expected, actual);
	}
	
	private Configurable createConfig() {
		return new Configurable(URL, URLTAGNAME, DOMAIN, TITLEQUERY, DESCRIPTIONQUERY, PRICEPERUNITQUERY, KCAL100GQUERY, VAT);
	}
	
	public static String readFile(String path, Charset encoding) throws IOException {
		  byte[] encoded = Files.readAllBytes(Paths.get(path));
		  return new String(encoded, encoding);
		}
}
