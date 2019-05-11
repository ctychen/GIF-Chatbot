package yt_music;

public class SearchedVideo {

	private String url;
	private String id;
	private String title;

	public SearchedVideo(String id, String title) {
		this.id = id;
		this.title = title;
		if (id == null) {
			this.url = null;
			System.out.println("Null ID");
		} else
			this.url = "http://www.youtube.com/watch?v=" + id;
	}

	public String getURL() {
		return this.url;
	}

	public void setURL(String id) {
		this.url = "http://www.youtube.com/watch?v=" + id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String t) {
		this.title = t;
	}

	public String getID() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
