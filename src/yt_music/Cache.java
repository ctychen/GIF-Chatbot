package yt_music;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cache {
	JSONObject dataBase;
	Path cacheFile = Paths.get("./cache.json");

	public Cache() {
		try {
			readDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readDataBase() throws IOException {
		if (dataBase != null)
			return;
		if (!Files.exists(cacheFile)) {
			Files.createFile(cacheFile);
			dataBase = new JSONObject();
			return;
		}
		try {
			dataBase = new JSONObject(new String(Files.readAllBytes(cacheFile)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeDatabase() {
		try {
			Files.write(cacheFile, dataBase.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean hasMethod(String key) {
		return dataBase.has(key);
	}

	public String getMethod(String key) {
		try {
			return dataBase.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void addMethod(String key, String method) {
		try {
			dataBase.put(key, method);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writeDatabase();
	}
}
