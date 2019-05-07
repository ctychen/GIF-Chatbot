package web;

import java.awt.List;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONException;

public class Tenor {
	
	final String BASE_URL = "https://api.tenor.com/v1/";
	final int resultLimit = 8;
	private String APIKey;
	private String locale = "en_US";
	private String filter = "high";
	
	public static final String APIKEY = "OTIWU6LM9JVL";
	
	/**
	 * Constructs tenor object with a default API key
	 */
	public Tenor() {
		
	}
	
	public Tenor(String APIKey) {
		this.APIKey = APIKey; //"H9U6TWFQY1LM"
	}
	
	/**
	 * 
	 * Fetches JSON code
	 * 
	 * @param url The EXACT same URL that you'd pass in when pinging tenor api (ex: "https://api.tenor.com/v1/search?q=fancy&key=H9U6TWFQY1LM&limit=8")
	 * @param ttl Time To Live in SECONDS, basically ignore all files older than this ttl
	 * @return JSON resulsts of the search querry if successful, null if not
	 */
	public String fetch(String url, int ttl) {
		String filename = "tenorJSON" + MindReader.fileSep + md5(url);
		FileTime ts;
		try {
			ts = Files.getLastModifiedTime(Paths.get(filename));
			LocalDateTime now = LocalDateTime.now();
			if (now.toEpochSecond(null) - ts.toMillis()/1000 < ttl) {
				return MindReader.read(filename); //MindReader reads the contents of the text file and returns the JSON with "\n" to separate lines
			}
		} catch (IOException e) {}
		String result = null;
		try {
			result = JSONTools.readJsonFromUrlAndPutItIntoAString(url);
		} catch (IOException e) {
			System.out.println("Something went wrong (IOException) with your fetch method call to " + url);
		} catch(JSONException e) {
			System.out.println("Something went wrong (JSONException) with your fetch method call to " + url);
		}
		
		if (result != null) {
			try {
				ArrayList<String> lines = JSONTools.toList(result);
				Files.write(Paths.get(filename), lines, Charset.forName("UTF-8")); //Creates a text file to store JSON
			} catch (IOException e) {
				System.out.println("Error creating file " + filename);
			}
		}
		
        return result;
	}
	
	/**
	 * Returns a JSON as a result of a query
	 * @param query A simple term, such as "fancy"
	 * @return JSON result of the query
	 */
	public String search(String query) { //anon_id may be used for rating
		
		return fetch(BASE_URL + "search?q" + query + "&key=" + APIKey + "&limit=" + resultLimit, 600); //URL should look something like "https://api.tenor.com/v1/search?q=fancy&key=H9U6TWFQY1LM&limit=8"
	}
	
	public String JSONDecode(String json) {
		return null;
	}

	/**
	 * Hashes the string into an md5
	 * @param s String to be hashed
	 * @return The md5 version of the string
	 */
	public String md5(String s) {
		try { 
			  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(s.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger n = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = n.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
	}
	
}
