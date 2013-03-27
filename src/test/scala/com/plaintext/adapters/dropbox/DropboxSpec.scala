package com.plaintext.adapters.dropbox

import com.dropbox.client2.DropboxAPI
import com.dropbox.client2.exception._
import com.dropbox.client2.session._
import org.scalatest.FlatSpec
import java.util.Properties
import java.io._

/**

*/
class DropboxSpec extends FlatSpec {

	val props = new Properties()
	props.load(this.getClass().getClassLoader().getResourceAsStream("dropbox.properties"))
	
	"Dropbox" should "upload a test file to the application folder" in {
 		val appKey = props.get("app.key").asInstanceOf[String]
		val appSecret = props.get("app.secret").asInstanceOf[String]	
		val accessTokenKey = props.get("access.token.key").asInstanceOf[String]
		val accessTokenSecret = props.get("access.token.secret").asInstanceOf[String]	
		val appName = props.get("app.name").asInstanceOf[String]	
			
		println("appKey: "+ appKey)	
		println("appSecret: "+ appSecret)
		println("accessTokenKey: "+ accessTokenKey)
		println("accessTokenSecret: "+ accessTokenSecret)
		println("appName: "+ appName)
			
		val appKeys = new AppKeyPair(appKey, appSecret)
		val session = new WebAuthSession(appKeys, Session.AccessType.APP_FOLDER) //Session.AccessType.DROPBOX)
		val mDBApi = new DropboxAPI[WebAuthSession](session)
	
		val reAuthTokens = new AccessTokenPair(accessTokenKey, accessTokenSecret);
		mDBApi.getSession().setAccessTokenPair(reAuthTokens);
			
		print("Uploading file...")
		val fileContents = "Hello World!"
		val inputStream = new ByteArrayInputStream(fileContents.getBytes())
		val newEntry = mDBApi.putFile("/testing.txt", inputStream, fileContents.length(), null, null)
		println("Done. \nRevision of file: " + newEntry.rev)
	}
}