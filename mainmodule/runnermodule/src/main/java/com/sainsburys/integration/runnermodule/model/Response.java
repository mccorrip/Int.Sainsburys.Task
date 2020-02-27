package com.sainsburys.integration.runnermodule.model;

import java.util.ArrayList;

import com.sainsburys.integration.parsingmodule.model.Result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
	private ArrayList<Result> results;
	private Total total;
	
	@Getter
	@Setter
	public
	 class Total {
		 private double gross;
		 private double vat;
		 
		 public Total(Double gross,Double vat) {
			 this.gross = gross;
			 this.vat = vat;
		 };
		 
	 }
	
	public Response(ArrayList<Result> results, Double vat) {
		this.results = results;
		this.total = buildTotal(results, vat);
		
	}

	public Total buildTotal(ArrayList<Result> results, Double vat) {
		Double gross = new Double(0.0);
		for(Result result: results) {
			gross = gross + result.getUnitPrice();
		}
		return new Total(gross, ((gross/(1 + vat))*vat));
	}
}
