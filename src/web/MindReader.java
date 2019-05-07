package web;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MindReader {
	public static final String fileSep = System.getProperty("file.separator");
	public static final String lineSep = System.getProperty("line.separator");
	/**
	 * Reads a text file
	 * 
	 * @param filename Has to be a text file of some sort, include the file extension in the filename
	 * @return The contents of the text file
	 */
	public static String read(String filename) throws IOException {
		String output = "";
		Scanner scanner = null;
		try {
			FileReader reader = new FileReader(filename);
			scanner = new Scanner(reader);
			
			while (scanner.hasNextLine())
				output += (scanner.nextLine()) + "\n";
			
			return output;
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}
}
