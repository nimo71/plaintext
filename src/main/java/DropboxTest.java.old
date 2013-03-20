/** 
 * A very basic dropbox example. 
 * 
 * http://berry120.blogspot.co.uk/2012/02/dropbox-java-api.html
 *
 * @author mjrb5 
 */ 
public class DropboxTest { 
	private static final String APP_KEY = "APP KEY"; 
	private static final String APP_SECRET = "SECRET KEY"; 
 	private static final AccessType ACCESS_TYPE = AccessType.APP_FOLDER; 
	private static DropboxAPI<WebAuthSession> mDBApi; 

	public static void main(String[] args) throws Exception { 
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET); 
		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE); 
		WebAuthInfo authInfo = session.getAuthInfo(); 
		RequestTokenPair pair = authInfo.requestTokenPair; 
		String url = authInfo.url; 
		Desktop.getDesktop().browse(new URL(url).toURI()); 
		
		JOptionPane.showMessageDialog(null, "Press ok to continue once you have authenticated."); 
		session.retrieveWebAccessToken(pair); 

		AccessTokenPair tokens = session.getAccessTokenPair(); 
		System.out.println("Use this token pair in future so you don't have to re-authenticate each time:"); 
		System.out.println("Key token: " + tokens.key); 
		System.out.println("Secret token: " + tokens.secret); 

		mDBApi = new DropboxAPI<WebAuthSession>(session); 
		System.out.println(); 
		System.out.print("Uploading file..."); 
		String fileContents = "Hello World!"; 
		ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContents.getBytes()); 
		Entry newEntry = mDBApi.putFile("/testing.txt", inputStream, fileContents.length(), null, null); 
		System.out.println("Done. \nRevision of file: " + newEntry.rev); 
	} 
}
