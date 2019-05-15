package yt_music;

import java.io.IOException;
import java.util.ArrayList;

public class Player {

	private static void play(String s) {
		try {
			System.out.println("Playing: " + s);
			Runtime.getRuntime().exec("vlc " + s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Usage: play <query>");
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (String string : args) {
			sb.append(string).append(" ");
		}

		System.out.println("Searching for " + sb.toString());

		ArrayList<Audio> vids = Youtube.search(sb.toString(), 1);
		if (vids == null || vids.size() == 0) {
			System.out.printf("No videos found for " + sb.toString());
			return;
		}

		String url = VidExtractor.getInstance().extractVideo(vids.get(0).getURL());
		if (url == null) {
			System.out.println("Extraction failed for: " + vids.get(0));
			return;
		}
		play(url);
	}

}
