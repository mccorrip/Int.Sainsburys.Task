package com.sainsburys.integration.parsingmodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestUtility {

	public static String readFile(String path) throws IOException {
		return readFile(path, StandardCharsets.UTF_8);
	}
	
	public static String readFile(String path, Charset encoding) throws IOException {
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}
	
	public static String removeWhiteSpaces(String input) {
	    return input.replaceAll("\\s+", "");
	}
	
}
