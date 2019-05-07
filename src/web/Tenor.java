package web;

public class Tenor {
	
	final String BASE_URL = "https://api.tenor.com/v1/";
	private String APIKey;
	private String locale;
	private String filter;
	
	public Tenor(String APIKey, String locale, String filter ) {
		this.APIKey = APIKey; //"H9U6TWFQY1LM"
		this.locale = locale; //"en_US"
		this.filter = filter; //"high"
	}
	
	
	public String fetch(String url, int ttl) {
		String filename = "/var/tmp/tenor_" + md5(url);
        return null;
	}
	
	public String search() {
		return null;
	}
	
	public String JSONDecode(String json) {
		return null;
	}

	public String md5(String s) {
		return null;
	}
	
	public void main(String[] args) {
		Tenor t = new Tenor("H9U6TWFQY1LM", "en_US_", "high");
		String result = t.search();
	}
	
}
