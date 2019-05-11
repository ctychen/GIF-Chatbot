package yt_music;

import org.json.JSONException;
import org.json.JSONObject;

public class Audio extends SearchedVideo implements Song {
	String mediaURL;
	String iconURL;

	public Audio(JSONObject obj) throws JSONException {
		this(obj.has("id") ? obj.getString("id") : null, obj.has("title") ? obj.getString("title") : null, null,
				obj.has("iconURL") ? obj.getString("iconURL") : null);
	}

	public Audio(String id, String title, String mediaURL, String iconURL) {
		super(id, title);
		this.mediaURL = mediaURL;
		this.iconURL = iconURL;
	}

	public Audio(SearchedVideo sv, String mediaURL) {
		super(sv.getID(), sv.getTitle());
		this.mediaURL = mediaURL;
	}

	public synchronized String getMediaURL() {
		if (!hasMediaURL()) {
			generateMediaURL();
		}
		return mediaURL;
	}

	public void generateMediaURL() {
		if (mediaURL != null) {
			return;
		}

		if (getURL() == null)
			generateURL();

		try {
			mediaURL = VidExtractor.getInstance().extractAudio(getURL());
			System.out.println(getURL() + " - " + getURL() + " - " + mediaURL);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(getTitle() + "\n" + getURL());
		}
	}

	protected void generateURL() {
		if (getURL() != null) {
			return;
		}

		Audio tmp = Youtube.search(getTitle(), 1).get(0);
		this.setURL(tmp.getURL());
		this.setTitle(tmp.getTitle());
	}

	public boolean hasMediaURL() {
		return mediaURL != null;
	}

	public String getIconURL() {
		return iconURL;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.putOpt("id", getID());
			obj.putOpt("title", getTitle());
			obj.putOpt("iconURL", iconURL);
		} catch (Exception e) {
			System.out.println("Oh boy I need to figure this method out");
		}
		return obj;
	}

}
