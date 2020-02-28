package com.sainsburys.integration.runnermodule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sainsburys.integration.parsingmodule.JSoupParser;
import com.sainsburys.integration.runnermodule.model.Response;
import com.sainsburys.integration.scrapermodule.JSoupScraper;

public class Main {
	public static void main(String[] args) throws Exception {
		Runner runner = new Runner();
		Response response = runner.init(new JSoupScraper(), new JSoupParser(), Configurable.init());
		ObjectMapper Obj = new ObjectMapper();
		System.out.println(Obj.writerWithDefaultPrettyPrinter().writeValueAsString(response));
	}
}
