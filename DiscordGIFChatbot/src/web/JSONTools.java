package web;

import java.awt.List;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility class for working with JSON files
 * 
 * @author clerdorf786
 */
public class JSONTools {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * Reads JSON from a URL
	 * 
	 * @param url Its just a URL, very simple
	 * @return A JSON object as a result from that URL
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	/**
	 * 
	 * @param url
	 * @return A string containing the JSON
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String readJsonFromUrlAndPutItIntoAString(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		System.out.println("URL=" + url);
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			return readAll(rd);
		} finally {
			is.close();
		}
	}

	/**
	 * Translates a string into a List, "\n" marks the end of each entry
	 * 
	 * @param s The string to be converted
	 * @return A List of strings
	 */
	public static ArrayList<String> toList(String s) {
		ArrayList<String> result = new ArrayList<String>();
		int k = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\n')
				k++;
			else {
				while (k >= result.size())
					result.add("");
				result.set(k, result.get(k) + s.charAt(i));
			}
		}
		return result;
	}

	/**
	 * @param JSON
	 * @return YouTube video search result as link
	 */
	public static String getVideoLink(String JSON) {
		String result = null;
		int fancyIndex = JSON.indexOf("data-context-item-id=") + 22;
		result = JSON.substring(fancyIndex, JSON.indexOf("\"", fancyIndex));

		return "https://www.youtube.com/watch?v=" + result;
	}
}