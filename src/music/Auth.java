package music;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.api.client.*;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import java.util.List;

public class Auth {

	public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();
	public static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";
	
	public static Credential authorize(List<String> scopes, String credentialDB) throws IOException {
		Reader clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/client_secrets.json"));
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);
		if (clientSecrets.getDetails().getClientId().startsWith("Enter")||clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
			System.out.println("Some wack google shit plez boi");
			System.exit(1);
		}
		
	}
}
