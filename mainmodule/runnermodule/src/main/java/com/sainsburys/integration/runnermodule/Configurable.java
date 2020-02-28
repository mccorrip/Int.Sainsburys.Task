package com.sainsburys.integration.runnermodule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.sainsburys.integration.runnermodule.model.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Configurable {
	private String url;
	private String urlTagName;
	private String domain;
	private String titleQuery;
	private String descriptionQuery;
	private String pricePerUnitQuery;
	private String kcal100gQuery;
	private Double vat;
	
	public static Configurable init() throws Exception {

		Properties prop = new Properties();
		String fileName = "config.properties";
		InputStream is = null;
		try {
		    is = new FileInputStream(fileName);
		} catch (FileNotFoundException ex) {
		    return createDefaultConfigurable();
		}
		try {
		    prop.load(is);
		} catch (IOException ex) {
		    throw new Exception("Properties failed to load");
		}
		return new Configurable(prop.getProperty("scraper.url"), prop.getProperty("parser.urlTagName"), prop.getProperty("parser.domain"), prop.getProperty("parser.titleQuery"), prop.getProperty("parser.descriptionQuery"), prop.getProperty("parser.pricePerUnitQuery"), prop.getProperty("parser.kcal100gQuery"), Double.parseDouble(prop.getProperty("vat")));
			
	}
	

	private static Configurable createDefaultConfigurable() {
		return new Configurable(Constants.URL, Constants.URLTAGNAME, Constants.DOMAIN, Constants.TITLEQUERY, Constants.DESCRIPTIONQUERY, Constants.PRICEPERUNITQUERY, Constants.KCAL100GQUERY, Constants.VAT);

	}
}
