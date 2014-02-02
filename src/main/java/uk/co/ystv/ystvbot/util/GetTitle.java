package uk.co.ystv.ystvbot.util;

/*
 * Author: Havard Rast Blok E-mail: Web : www.rememberjava.com
 */

import java.io.*;
import java.net.*;

/**
 * This small application takes an URL as argument, downloads that web page and
 * outputs its HTML title to the screen.
 * 
 * Refer to the main() method for how to read a local file instead.
 */
public class GetTitle {
	private static String startTag = "<title>";
	private static String endTag = "</title>";
	private static int startTagLength = startTag.length();

	public static String[] get(String string) {
		return get(string, 0);
	}
	
	public static String[] get(String string, int depth) {
		if (depth > 10) {
			return null;
		}
		BufferedReader bufReader;
		String line;
		boolean foundStartTag = false;
		boolean foundEndTag = false;
		int startIndex, endIndex;
		String title = "";

		try {
			if (!string.matches("^([A-Za-z]{3,9}:(?:\\/\\/)?)(.*)")) {
				string = "http://" + string;
			}
			URL url = new URL(string);
			// open file
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			int status = conn.getResponseCode();
			if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER) {
				return get(conn.getHeaderField("Location"), depth + 1);
			}
			bufReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			// read line by line
			while ((line = bufReader.readLine()) != null && !foundEndTag) {
				// System.out.println(line);

				// search for title start tag (convert to lower case before
				// searhing)
				if (!foundStartTag && (startIndex = line.toLowerCase().indexOf(startTag)) != -1) {
					foundStartTag = true;
				} else {
					// else copy from start of string
					startIndex = -startTagLength;
				}

				// search for title start tag (convert to lower case before
				// searhing)
				if (foundStartTag && (endIndex = line.toLowerCase().indexOf(endTag)) != -1) {
					foundEndTag = true;
				} else {
					// else copy to end of string
					endIndex = line.length();
				}

				// extract title field
				if (foundStartTag || foundEndTag) {
					// System.out.println("foundStartTag="+foundStartTag);
					// System.out.println("foundEndTag="+foundEndTag);
					// System.out.println("startIndex="+startIndex);
					// System.out.println("startTagLength="+startTagLength);
					// System.out.println("endIndex="+endIndex);

					title += line.substring(startIndex + startTagLength, endIndex);
				}
			}

			// close the file when finished
			bufReader.close();

			// output the title
			if (title.length() > 0) {
				return new String[] {title, string};
			} else {
				return null;
			}

		} catch (IOException e) {
			return null;
		}
	}
}
