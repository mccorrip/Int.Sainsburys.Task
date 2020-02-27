package com.sainsburys.integration.parsingmodule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Result {
	private String title;
	private Integer kcal_per_100g;
	private Double unitPrice;
	private String description;
}
