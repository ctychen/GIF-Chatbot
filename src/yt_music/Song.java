package yt_music;

import org.json.JSONObject;

public interface Song{

	    public JSONObject toJSONObject();
	    
		public boolean hasMediaURL();

		public String getMediaURL();

		public void generateMediaURL();

		public String getIconURL();
		
		public String getTitle();

	}

