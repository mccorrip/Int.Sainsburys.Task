package com.sainsburys.integration.runnermodule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sainsburys.integration.parsingmodule.JSoupParser;
import com.sainsburys.integration.runnermodule.model.Response;
import com.sainsburys.integration.scrapermodule.JSoupScraper;

public class Main {
	public static void main(String[] args) throws Exception {
		Runner runner = new Runner();
		Response response = runner.init(new JSoupScraper(), new JSoupParser(), initConfig());
		ObjectMapper Obj = new ObjectMapper();
		System.out.println(Obj.writerWithDefaultPrettyPrinter().writeValueAsString(response));
	}

	private static Configurable initConfig() throws Exception {
		Properties prop = new Properties();
		String fileName = "src/main/resources/config.properties";
		InputStream is = null;
		try {
		    is = new FileInputStream(fileName);
		} catch (FileNotFoundException ex) {
		    throw new Exception("Properties file not found");
		}
		try {
		    prop.load(is);
		} catch (IOException ex) {
		    throw new Exception("Properties failed to load");
		}
		return new Configurable(prop.getProperty("scraper.url"), prop.getProperty("parser.urlTagName"), prop.getProperty("parser.domain"), prop.getProperty("parser.titleQuery"), prop.getProperty("parser.descriptionQuery"), prop.getProperty("parser.pricePerUnitQuery"), prop.getProperty("parser.kcal100gQuery"), Double.parseDouble(prop.getProperty("vat")));
	}
}
