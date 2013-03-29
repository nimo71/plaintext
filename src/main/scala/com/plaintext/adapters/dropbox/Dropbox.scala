package com.plaintext.adapters.dropbox

import com.dropbox.client2.DropboxAPI
import com.dropbox.client2.session._
import java.util.Properties
import java.io._

object Dropbox {

	
	def apply(accessTokenKey: String, accessTokenSecret: String): Dropbox = 
	{
		val props = new Properties()
		props.load(this.getClass().getClassLoader().getResourceAsStream("dropbox.properties"))
		
		val appKey = props.get("app.key").asInstanceOf[String]
		val appSecret = props.get("app.secret").asInstanceOf[String]	
		val appName = props.get("app.name").asInstanceOf[String]	
		
		new Dropbox(appKey, appSecret, accessTokenKey, accessTokenSecret)
	}
}

class Dropbox(
	val appKey: String, 
	val appSecret: String, 
	val accessTokenKey: String, 
	val accessTokenSecret: String ) 
{
	val appKeys = new AppKeyPair(appKey, appSecret)
	val session = new WebAuthSession(appKeys, Session.AccessType.APP_FOLDER) //Session.AccessType.DROPBOX)
	val mDBApi = new DropboxAPI[WebAuthSession](session)

	val reAuthTokens = new AccessTokenPair(accessTokenKey, accessTokenSecret)
	mDBApi.getSession().setAccessTokenPair(reAuthTokens)
	
	def put(path: String, content: String): DropboxFile = {
		val inputStream = new ByteArrayInputStream(content.getBytes())
		val entry = mDBApi.putFile(path, inputStream, content.length(), null, null)
		new DropboxFile(mDBApi, entry)
	}
}