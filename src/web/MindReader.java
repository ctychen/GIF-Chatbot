package web;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

/**
 * Reads text files
 */
public class MindReader {
	public static final String fileSep = System.getProperty("file.separator");
	public static final String lineSep = System.getProperty("line.separator");

	/**
	 * Reads a text file
	 * 
	 * @param filename Has to be a text file of some sort, include the file
	 *                 extension in the filename
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

	/**
	 * Deletes a file based on the filename, which includes the path
	 * 
	 * @param filename The name and path of the file to be deleted
	 * @throws SecurityException This exception will only be thrown if this method
	 *                           does not have delete access to that file
	 * @return True if successfully deleted, else false
	 */
	public static boolean erase(String filename) throws SecurityException {
		File f = new File(filename);
		if (f.delete()) {
			System.out.println("File " + filename + " successfully deleted");
			return true;
		} else {
			System.out.println("Error deleting file " + filename);
			return false;
		}

	}

	public static int folderClean(String folderpath, int ttl) throws SecurityException, IOException {
		File folder = new File(folderpath);
		File[] listOfFiles = folder.listFiles();
		FileTime ts;
		LocalDateTime now = LocalDateTime.now();
		int total = 0;
		System.out.println("Folder clean for " + folderpath);
		for (File e : listOfFiles) {
			ts = Files.getLastModifiedTime(Paths.get(folderpath + fileSep + e.getName()));
			if (e.isFile() && now.toEpochSecond(ZoneOffset.UTC) + 25200 - ts.toMillis() / 1000 > ttl) {
				if (erase(folderpath + fileSep + e.getName()))
					total++;
			}
		}
		return total;
	}
}