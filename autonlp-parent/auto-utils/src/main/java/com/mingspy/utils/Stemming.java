package com.mingspy.utils;

public class Stemming {
	public static String stem(String line) {
		line = line.toLowerCase();
		// Strip all HTML
		// Looks for any expression that starts with < and ends with > and replace
		// and does not have any < or > in the tag it with a space
		line = line.replaceAll("<[^<>]+>", "");

		// Handle Numbers
		// Look for one or more characters between 0-9
		line = line.replaceAll("[0-9]+", "SLOTnumber");

		// Handle URLS
		// Look for strings starting with http:// or https://

		line = line.replaceAll("(http|https)://[^\\s]*", "SLOThttpaddr");
		line = line.replaceAll("www.[^\\s]*", "SLOThttpaddr");
		// Handle Email Addresses
		// Look for strings with @ in the middle
		line = line.replaceAll("[^\\s]+@[^\\s]+", "SLOTemailaddr");

		// Handle $ sign
		line = line.replaceAll("[$]+", "SLOTdollar");
		line = line.replaceAll("[￥]+", "SLOTdollar");
		return line;
	}
}
