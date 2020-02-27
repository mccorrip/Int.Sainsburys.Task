package com.sainsburys.integration.runnermodule;

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
	
}
