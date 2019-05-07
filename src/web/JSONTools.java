package web;

import java.awt.List;
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
	   * Reads JSON from a ULR
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
	   * Self Explanatory
	   * @param url
	   * @return A string containing the JSON
	   * @throws IOException
	   * @throws JSONException
	   */
	  public static String readJsonFromUrlAndPutItIntoAString(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      return readAll(rd);
		    } finally {
		      is.close();
		    }
		  }
	  /**
	   * Translates a string into a List, "\n" marks the end of each entry
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
				  if (k > result.size())
					  result.add("");
				  result.set(k, result.get(i) + s.charAt(i));
			  }
		  }
		  return result;
	  }
}
