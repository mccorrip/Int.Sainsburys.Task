package com.sainsburys.integration.runnermodule.model;

public class Constants {
	public static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
	public static final String URLTAGNAME = "div.productInfo a";
	public static final String DOMAIN = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk";
	public static final String TITLEQUERY = "div.productTitleDescriptionContainer h1";
	public static final String DESCRIPTIONQUERY = "div.productText p";
	public static final String PRICEPERUNITQUERY = "div.pricingAndTrolleyOptions p.pricePerUnit";
	public static final String KCAL100GQUERY = "table.nutritionTable tr.tableRow0 td, table.nutritionTable tr:nth-child(2) td";
	public static final Double VAT = 0.2;
}
