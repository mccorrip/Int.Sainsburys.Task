package com.sainsburys.integration.runnermodule.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import com.sainsburys.integration.parsingmodule.model.Result;



public class TestResponse {
	
	@Test
	public void testBuildTotal() {
		ArrayList<Result> results = buildResults(100, 100, 100);
		Response response = new Response(results, 0.2);
		assertEquals(new Double(50), new Double(response.getTotal().getVat()));
	}
	
	@Test
	public void testBuildTotal2() {
		ArrayList<Result> results = buildResults(30, 20, 10);
		Response response = new Response(results, 0.2);
		assertEquals(new Double(10), new Double(response.getTotal().getVat()));
	}

	private ArrayList<Result> buildResults(double price1, double price2, double price3) {
		ArrayList<Result> results = new ArrayList<Result>();
		Result result1 = new Result(null, null, price1, null);
		Result result2 = new Result(null, null, price2, null);
		Result result3 = new Result(null, null, price3, null);
		results.add(result1);
		results.add(result2);
		results.add(result3);
		return results;
	}
	
}
