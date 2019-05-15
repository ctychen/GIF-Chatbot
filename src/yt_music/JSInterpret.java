package yt_music;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSInterpret {

	public static Pattern VAR_PATTERN = Pattern.compile("var \\w{2}=\\{(.*?)\\};");
	public static Pattern FUNCTION_PATTERN = Pattern.compile("function main\\(a\\)\\{(.*?)\\};");
	public static Pattern SUBFUNCTION_PATTERN = Pattern.compile("(\\w{2}):function\\(a,?b?\\)\\{(.*?)\\}");
	public static Pattern FUNCTION_ELEMENT_PATTERN = Pattern.compile("\\w{2}\\.(\\w{2})\\(a,(\\d{1,2})\\)");

	public String execute(String method, String arg) throws ParseException {
		String function = extractFirst(FUNCTION_PATTERN, method);
		String var = extractFirst(VAR_PATTERN, method);
		HashMap<String, String> map = getSubFunctionMap(var);
		char[] input = arg.toCharArray();

		String[] functionElems = function.split(";");
		functionElems = Arrays.copyOfRange(functionElems, 1, functionElems.length - 1);
		Matcher m;
		for (String func : functionElems) {

			if ((m = FUNCTION_ELEMENT_PATTERN.matcher(func)).find()) {
				arg = interp(map.get(m.group(1)), arg, m.group(2));
			} else {
			}
		}

		return arg;
	}

	private HashMap<String, String> getSubFunctionMap(String var) {
		HashMap<String, String> map = new HashMap();
		Matcher m = SUBFUNCTION_PATTERN.matcher(var);
		while (m.find()) {
			map.put(m.group(1), m.group(2));
		}
		return map;
	}

	private String extractFirst(Pattern p, String content) {
		Matcher m = p.matcher(content);
		m.find();
		return m.group(1);
	}

	private String interp(String function, String a, String b) throws ParseException {
		if (function.equals("a.reverse()")) {
			char[] chars = a.toCharArray();
			ArrayUtils.reverse(chars);
			a = new String(chars);
			return a;
		} else if (function.equals("a.splice(0,b)")) {
			return a.substring(Integer.parseInt(b));
		} else if (function.startsWith("var")) {
			HashMap<String, String> vars = new HashMap<>();
			vars.put("a", a);
			vars.put("b", b);
			String[] elements = function.split(";");

			Matcher m;
			for (String e : elements) {
				if ((m = a1.matcher(e)).find()) {
					vars.put(m.group(1), new String(new char[] { parseSubExpression(m.group(2), vars) }));
				} else if ((m = a2.matcher(e)).find()) {
					char[] chars = vars.get(m.group(1)).toCharArray();
					chars[Integer.parseInt(m.group(2))] = parseSubExpression(m.group(3), vars);
					vars.put(m.group(1), new String(chars));
				} else if ((m = a3.matcher(e)).find()) {
					char[] chars = vars.get(m.group(1)).toCharArray();
					chars[Integer.parseInt(vars.get(m.group(2)))] = parseSubExpression(m.group(3), vars);
					vars.put(m.group(1), new String(chars));
				} else {
					throw new ParseException("Expression not be parsed: " + e);
				}
			}
			return vars.get("a");
		}
		throw new ParseException("Function could not be parsed: " + function);
	}

	final static Pattern a1 = Pattern.compile("var (\\w)=(.*)");
	final static Pattern a2 = Pattern.compile("(\\w)\\[(\\d{1,2})\\]=(.*)");
	final static Pattern a3 = Pattern.compile("(\\w)\\[(\\w)\\]=(.*)");

	final static Pattern p1 = Pattern.compile("(\\w)\\[(\\d{1,2})\\]");
	final static Pattern p2 = Pattern.compile("(\\w)\\[(\\w)%(\\w)\\.length\\]");

	private char parseSubExpression(String expression, HashMap<String, String> vars) throws ParseException {
		if (expression.length() == 1)
			return vars.get(expression).charAt(0);

		Matcher m;
		if ((m = p1.matcher(expression)).find()) {
			return vars.get(m.group(1)).charAt(Integer.parseInt(m.group(2)));
		} else if ((m = p2.matcher(expression)).find()) {
			return vars.get(m.group(1)).charAt(Integer.parseInt(vars.get(m.group(2))) % vars.get(m.group(3)).length());
		}
		throw new ParseException("Subexpression not be parsed: " + expression);
	}
}

class ParseException extends Exception {
	public ParseException(String msg) {
		super(msg);
	}
}
