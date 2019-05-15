package yt_music;

import yt_music.JSInterpret;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VidExtractor {

	final static Pattern FMT_MAP_PATTERN = Pattern.compile("ytplayer\\.config = (\\{.*?\\});");
	final static Pattern HTML5PLAYER_METHOD_NAME_PATTERN = Pattern.compile("sig\\|\\|(..)\\(");
	final static Pattern SECONDARY_METHOD_PATTERN = Pattern.compile(";(\\w{2})\\.\\w{2}\\(");
	final static Pattern FMT_URL_PATTERN = Pattern.compile("url=(.*?)(&|$)");
	final static Pattern FMT_SIG_PATTERN = Pattern.compile("(^|&)s=(.*?)(&|$)");
//    final static ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("javascript");
	private static VidExtractor ve;
	private Cache cache;

	private VidExtractor() {
		cache = new Cache();
	}

	public static synchronized VidExtractor getInstance() {
		if (ve == null) {
			ve = new VidExtractor();
		}
		return ve;
	}

	public String extractAudio(String youtubeURL) throws Exception {
		System.out.print("Downloading " + youtubeURL + " ...");
		String page = IOUtils.toString(new URL(youtubeURL.replace("http:", "https:")).openStream());
		System.out.println("OK");

		Matcher m = FMT_MAP_PATTERN.matcher(page);
		if (!m.find())
			throw new ExtractionError("No FMT map found");

		JSONObject root = new JSONObject(m.group(1));
		String adaptiveFmts = getAdaptiveFmtsAudio(root);
		String streamURL = getStreamUrl(adaptiveFmts) + "&ratebypass=yes";
		;

		if (streamURL.contains("signature="))
			return streamURL;

		String encryptedSignature = getSig(adaptiveFmts);
		String html5PlayerURL = getHtml5PlayerURL(root);
		return prepareStream(root, streamURL, encryptedSignature, html5PlayerURL);
	}

	public String extractVideo(String youtubeURL) throws Exception {
		System.out.print("Downloading " + youtubeURL + " ...");
		String page = IOUtils.toString(new URL(youtubeURL.replace("http:", "https:")).openStream());
		System.out.println("OK");

		Matcher m = FMT_MAP_PATTERN.matcher(page);
		if (!m.find())
			throw new ExtractionError("No FMT map found");

		JSONObject root = new JSONObject(m.group(1));
		String fmtStreamMap = getFmtStreamMap(root);
		String streamURL = getStreamUrl(fmtStreamMap);
		if (!streamURL.contains("ratebypass"))
			streamURL = streamURL + "&ratebypass=yes";

		if (streamURL.contains("signature="))
			return streamURL;

		String encryptedSignature = getSig(fmtStreamMap);
		String html5PlayerURL = getHtml5PlayerURL(root);
		return prepareStream(root, streamURL, encryptedSignature, html5PlayerURL);
	}

	private String prepareStream(JSONObject root, String streamURL, String encryptedSignature, String html5PlayerURL)
			throws Exception {
		String playerID = html5PlayerURL.split("/")[5];
		String method;
		if (cache.hasMethod(playerID)) {
			System.out.println("Get " + playerID + " from cache");
			method = cache.getMethod(playerID);
		} else {
			System.out.print("Downloading " + html5PlayerURL + " ...");
			String html5Player = IOUtils.toString(new URL(html5PlayerURL));
			System.out.println("OK");
			String methodName = getMethodName(html5Player);
			method = getMethod(methodName, html5Player);
			cache.addMethod(playerID, method);
		}

		String signature = new JSInterpret().execute(method, encryptedSignature);
		return streamURL + "&signature=" + signature;
	}

	public static String escape(String input) {
		StringBuilder output = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			int chx = (int) ch;

			// let's not put any nulls in our strings
			assert (chx != 0);

			if (ch == '\n') {
				output.append("\\n");
			} else if (ch == '\t') {
				output.append("\\t");
			} else if (ch == '\r') {
				output.append("\\r");
			} else if (ch == '\\') {
				output.append("\\\\");
			} else if (ch == '"') {
				output.append("\\\"");
			} else if (ch == '\b') {
				output.append("\\b");
			} else if (ch == '\f') {
				output.append("\\f");
			} else if (chx >= 0x10000) {
				assert false : "Java stores as u16, so it should never give us a character that's bigger than 2 bytes. It literally can't.";
			} else if (chx > 127) {
				output.append(String.format("\\u%04x", chx));
			} else {
				output.append(ch);
			}
		}

		return output.toString();
	}

	public static String unescape(String input) {
		StringBuilder builder = new StringBuilder();

		int i = 0;
		while (i < input.length()) {
			char delimiter = input.charAt(i);
			i++; // consume letter or backslash

			if (delimiter == '\\' && i < input.length()) {

				// consume first after backslash
				char ch = input.charAt(i);
				i++;

				if (ch == '\\' || ch == '/' || ch == '"' || ch == '\'') {
					builder.append(ch);
				} else if (ch == 'n')
					builder.append('\n');
				else if (ch == 'r')
					builder.append('\r');
				else if (ch == 't')
					builder.append('\t');
				else if (ch == 'b')
					builder.append('\b');
				else if (ch == 'f')
					builder.append('\f');
				else if (ch == 'u') {

					StringBuilder hex = new StringBuilder();

					// expect 4 digits
					if (i + 4 > input.length()) {
						throw new RuntimeException("Not enough unicode digits! ");
					}
					for (char x : input.substring(i, i + 4).toCharArray()) {
						if (!Character.isLetterOrDigit(x)) {
							throw new RuntimeException("Bad character in unicode escape.");
						}
						hex.append(Character.toLowerCase(x));
					}
					i += 4; // consume those four digits.

					int code = Integer.parseInt(hex.toString(), 16);
					builder.append((char) code);
				} else {
					throw new RuntimeException("Illegal escape sequence: \\" + ch);
				}
			} else { // it's not a backslash, or it's the last character.
				builder.append(delimiter);
			}
		}

		return builder.toString();
	}

	private String getAdaptiveFmtsFirst(JSONObject root) {
		String encodedFmtStreamMap = " ";
		try {
			encodedFmtStreamMap = root.getJSONObject("args").getString("adaptive_fmts");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return unescape(encodedFmtStreamMap.split(",")[0]);
	}

	private String getAdaptiveFmtsAudio(JSONObject root) {
		String encodedFmtStreamMap = " ";
		try {
			encodedFmtStreamMap = root.getJSONObject("args").getString("adaptive_fmts");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] streams = encodedFmtStreamMap.split(",");
		String audioStream = null;
		for (String stream : streams) {
			if (stream.contains("type=audio")) {
				audioStream = stream;
			}
		}
		return StringEscapeUtils.unescapeJava(audioStream);
	}

	private String getFmtStreamMap(JSONObject root) {
		String encodedFmtStreamMap = " ";
		try {
			encodedFmtStreamMap = root.getJSONObject("args").getString("url_encoded_fmt_stream_map");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return unescape(encodedFmtStreamMap.split(",")[0]);
	}

	private String getHtml5PlayerURL(JSONObject root) {
		String url = " ";
		try {
			url = "https:" + root.getJSONObject("assets").getString("js");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

	private String getMethodName(String html5Player) throws ExtractionError {
		Matcher matcher = HTML5PLAYER_METHOD_NAME_PATTERN.matcher(html5Player);
		if (matcher.find()) {
			return matcher.group(1);
		}
		throw new ExtractionError("Method name not found");
	}

	private String getMethod(String methodName, String html5player) throws ExtractionError {
		Pattern methodPattern = Pattern.compile("function " + methodName + "\\(.\\)\\{(.*?)\\};");
		Matcher methodMatcher = methodPattern.matcher(html5player);
		if (!methodMatcher.find())
			throw new ExtractionError("Method not found: " + methodName);

		String method = methodMatcher.group();
		method = method.replace("function " + methodName, "function main");

		Matcher secondaryMethodMatcher = SECONDARY_METHOD_PATTERN.matcher(method);

		if (secondaryMethodMatcher.find()) {
			String secondaryMethodName = secondaryMethodMatcher.group(1);
			if (secondaryMethodName.equals(methodName))
				return method;
			return method + getVar(secondaryMethodMatcher.group(1), html5player);
		} else {
			return method;
		}
	}

	private String getVar(String varName, String html5player) throws ExtractionError {
		Pattern varPattern = Pattern.compile("var " + varName + "=\\{(.*?)\\};");
		Matcher varMatcher = varPattern.matcher(html5player);
		if (!varMatcher.find())
			throw new ExtractionError("Var not found " + varName);
		return varMatcher.group();
	}

	private String getSig(String fmtStreamMap) throws ExtractionError {
		Matcher m = FMT_SIG_PATTERN.matcher(fmtStreamMap);
		if (!m.find())
			throw new ExtractionError("Signature not found: \n" + fmtStreamMap);
		return m.group(2);
	}

	private String getStreamUrl(String fmtStreamMap) throws ExtractionError, UnsupportedEncodingException {
		Matcher m = FMT_URL_PATTERN.matcher(fmtStreamMap);
		if (!m.find())
			throw new ExtractionError("Stream URL not found");
		return URLDecoder.decode(m.group(1), "UTF-8");
	}
}

class ExtractionError extends Exception {
	public ExtractionError(String msg) {
		super(msg);
	}

}
