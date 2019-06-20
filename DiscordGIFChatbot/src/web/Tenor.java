package web;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import org.json.JSONException;

/**
 * Fetches GIFs matching a certain keyword using the Tenor API
 * 
 * @author clerdorf786, cchen351
 *
 */
public class Tenor {

	private final String BASE_URL = "https://api.tenor.com/v1/";
	private int resultLimit = 10;
	private String APIKey;
	private String locale = "en_US";
	private String filter = "medium"; // G + PG
	private final String[] resolutions = { "nanogif", "tinygif", "mediumgif", "gif" };
	private int res = 2;

	/**
	 * Constructs tenor object with the default Tenor API key
	 */
	public Tenor() {
		this.APIKey = "OTIWU6LM9JVL";
	}

	/**
	 * Constructs tenor object with your own Tenor API
	 * 
	 * @param APIKey Your Tenor API Key
	 */
	public Tenor(String APIKey) {
		this.APIKey = APIKey;
	}

	/**
	 * Sets content filter from user input
	 * 
	 * @param filter; high for G, medium for G/PG, low for G/PG/PG-13, off for no
	 *        filter
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * 
	 * @param res The new resolution (0-nanogif, 1-tinygif, 2-mediumgif, 3-gif)
	 */
	public void setRes(int res) {
		this.res = res % 4;
	}

	public void setResultLimit(int resultLimit) {
		this.resultLimit = resultLimit;
	}

	/**
	 * 
	 * @return Returns the current resolution as a string (Ex: nanogif)
	 */
	public String getRes() {
		return resolutions[res];
	}

	/**
	 * 
	 * Fetches JSON code
	 * 
	 * @param url The EXACT same URL that you'd pass in when pinging tenor api (ex:
	 *            "https://api.tenor.com/v1/search?q=fancy&key=H9U6TWFQY1LM&limit=8")
	 * @param ttl Time To Live in SECONDS, basically ignore all files older than
	 *            this ttl
	 * @return JSON results of the search query if successful, null if not
	 */
	public String fetch(String url, int ttl) {
		File f = new File(System.getProperty("user.dir") + MindReader.fileSep + "tenorJSON");
		try {
			if (f.mkdir()) {
				System.out.println("Directory Created");
			} else {
				System.out.println("Directory is not created");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ttl = 0;
		String filename = System.getProperty("user.dir") + MindReader.fileSep + "tenorJSON" + MindReader.fileSep
				+ md5(url) + ".JSON";
		FileTime ts;
		try {
			ts = Files.getLastModifiedTime(Paths.get(filename));
			LocalDateTime now = LocalDateTime.now();
			if (now.toEpochSecond(ZoneOffset.UTC) + 25200 - ts.toMillis() / 1000 < ttl) {
				// System.out.println("Found JSON in cache " + now.toEpochSecond(ZoneOffset.UTC)
				// + " " + (ts.toMillis() / 1000));
				return MindReader.read(filename); // MindReader reads the contents of the text file and returns the JSON
													// with "\n" to separate lines
			} else {
				MindReader.erase(filename); // Deletes the file if it is too old
			}
		} catch (IOException e) {
		}
		String result = null;
		try {
			result = JSONTools.readJsonFromUrlAndPutItIntoAString(url);
		} catch (IOException e) {
			System.out.println("Something went wrong (IOException) with your fetch method call to " + url);
		} catch (JSONException e) {
			System.out.println("Something went wrong (JSONException) with your fetch method call to " + url);
		}

		if (result != null) {
			try {
				ArrayList<String> lines = JSONTools.toList(result);
				Files.write(Paths.get(filename), lines, Charset.forName("UTF-8")); // Creates a text file to store JSON
			} catch (IOException e) {
				System.out.println("Error creating file " + filename);
			}
		}

		return result;
	}

	/**
	 * Returns a JSON as a result of a query
	 * 
	 * @param query A simple term, such as "fancy"
	 * @return JSON result of the query.
	 * 
	 */
	public String search(String query) { // anon_id may be used for rating
		
		return fetch(BASE_URL + "search?q=" + query + "&key=" + APIKey + "&limit=" + resultLimit + "&contentfilter="
				+ filter, 600); // URL should be
								// https://api.tenor.com/v1/search?q=fancy&key=H9U6TWFQY1LM&limit=8
	}

	/**
	 * Given a json, finds a random gif url and returns it
	 * 
	 * @param json THe JSON to find a URL in
	 * @return A URL to a random gif present in the inputted JSON, if it can't find
	 *         one then it returns null
	 */
	public String getGIFURL(String json) {
		String result = null;
		int count = 0;
		int fancyIndex = -1;
		while (fancyIndex == -1 && count < 60) {
			fancyIndex = (json.indexOf("\"" + resolutions[res] + "\":", (int) (Math.random() * json.length())));
			count++;
		}
		if (fancyIndex != -1)
			result = json.substring(json.indexOf("\"url\":", fancyIndex) + 8,
					json.indexOf(".gif\",", fancyIndex + 1) + 4);
		return result;
	}

	/**
	 * Given a URL, returns a filename for a GIF stored locally, if no such GIF
	 * exists, it downloads it from the URL and creates it under a filename equal to
	 * the md5 of the url
	 * 
	 * @param url The url of the image
	 * @return A filename for a local image
	 */
	public String getGIFFromLocal(String url, int ttl) { // Credit :
															// https://www.programcreek.com/2012/12/download-image-from-url-in-java/
		String result = null;
		// ttl = 0;
		String filename = System.getProperty("user.dir") + MindReader.fileSep + "images" + MindReader.fileSep + md5(url)
				+ ".gif";
		FileTime ts;
		try {
			ts = Files.getLastModifiedTime(Paths.get(filename));
			LocalDateTime now = LocalDateTime.now();
			if (now.toEpochSecond(ZoneOffset.UTC) + 25200 - ts.toMillis() / 1000 < ttl) {
				return filename;
			} else {
				MindReader.erase(filename); // Deletes image if too old
			}
		} catch (IOException e) {
		}

		InputStream is = null;
		OutputStream os = null;

		File f = new File(System.getProperty("user.dir") + MindReader.fileSep + "images");
		try {
			if (f.mkdir()) {
				System.out.println("Directory Created");
			} else {
				System.out.println("Directory is not created");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			URL fancyUrl = new URL(url);
			String destName = System.getProperty("user.dir") + MindReader.fileSep + "images" + MindReader.fileSep
					+ md5(url) + ".gif";
			System.out.println("Creating file: " + destName);

			is = new BufferedInputStream(fancyUrl.openStream());
			os = new BufferedOutputStream(new FileOutputStream(destName));

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			result = destName;
		} catch (IOException e) {
			System.out.println("Error downloading file from URL: " + url);
			System.out.println(e);
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error closing InputStream or OutputStream");
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Hashes the string into an md5
	 * 
	 * @param s String to be hashed
	 * @return The md5 version of the string
	 */
	public String md5(String s) {
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
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

	public static void main(String args[]) {
		Tenor yeet = new Tenor();
		// System.out.println(yeet.search("fancy"));
		System.out.println(yeet.getGIFFromLocal(yeet.getGIFURL(yeet.search("fancy")), 600));
	}

}