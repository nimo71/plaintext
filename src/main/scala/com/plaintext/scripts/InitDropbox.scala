package com.plaintext.scripts

import com.dropbox.client2.DropboxAPI
import com.dropbox.client2.session._
import java.util.Properties
import java.io._


object InitDropbox {
	def main(args: Array[String]) {
		val props = new Properties()
		props.load(this.getClass().getClassLoader().getResourceAsStream("dropbox.properties"))

 		val appKey = props.get("app.key").asInstanceOf[String]
		val appSecret = props.get("app.secret").asInstanceOf[String]	
		val accessTokenKey = props.get("access.token.key").asInstanceOf[String]
		val accessTokenSecret = props.get("access.token.secret").asInstanceOf[String]	
		val appName = props.get("app.name").asInstanceOf[String]	
		
		val appKeys = new AppKeyPair(appKey, appSecret)
		val session = new WebAuthSession(appKeys, Session.AccessType.APP_FOLDER)
		val mDBApi = new DropboxAPI[WebAuthSession](session)
		
		println("Please go to this URL and hit \"Allow\": " + mDBApi.getSession().getAuthInfo().url)	
		println("Once you have allowed the app, hit R and <enter> to continue...")
		
		while (!(readLine equals "r")) { Unit }
				
		val tokenPair = mDBApi.getSession().getAccessTokenPair()
		val tokens = new RequestTokenPair(tokenPair.key, tokenPair.secret)
		mDBApi.getSession().retrieveWebAccessToken(tokens)
				
		println("Add token key and secret to the dropbox.properties file")
		println("Access Token Key: "+ session.getAccessTokenPair().key)
 		println("Access Token Secret: "+ session.getAccessTokenPair().secret)
	}
}


/**
// The APP_KEY & SECRET are provided by Dropbox when you create an app
// using their developer console
String APP_KEY = "PUT_YOUR_APP_KEY_HERE";
String APP_SECRET = "PUT_YOUR_APP_SECRET_HERE";

AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
DropboxAPI mDBApi;
AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
mDBApi = new DropboxAPI(session);
System.out.println("Please go to this URL and hit \"Allow\": " + mDBApi.getSession().getAuthInfo().url); // tell user to go to app allowance URL
AccessTokenPair tokenPair = mDBApi.getSession().getAccessTokenPair();
// wait for user to allow app in above URL, 
// then return back to executing code below
RequestTokenPair tokens = new RequestTokenPair(tokenPair.key, tokenPair.secret);
mDBApi.getSession().retrieveWebAccessToken(tokens); // completes initial auth

//these two calls will retrive access tokens for future use
session.getAccessTokenPair().key    // store String returned by this call somewhere
session.getAccessTokenPair().secret // same for this line
*/ 