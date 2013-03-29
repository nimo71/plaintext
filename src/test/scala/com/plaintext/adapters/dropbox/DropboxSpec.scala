package com.plaintext.adapters.dropbox

import com.dropbox.client2.DropboxAPI
import com.dropbox.client2.exception._
import com.dropbox.client2.session._
import org.scalatest.FlatSpec
import java.util.Properties

/**

*/
class DropboxSpec extends FlatSpec {

	val props = new Properties()
	props.load(this.getClass().getClassLoader().getResourceAsStream("dropbox.properties"))

	"Dropbox" should "upload a test file to the application folder" in {
		val accessTokenKey = props.get("access.token.key").asInstanceOf[String]
		val accessTokenSecret = props.get("access.token.secret").asInstanceOf[String]	
			
		print("Uploading file...")
		
		val dbxFilePath = "/testing.txt"
		val fileContents = "Hello World!"
		val dropboxFile = Dropbox(accessTokenKey, accessTokenSecret).put(dbxFilePath, fileContents)
			
		print("Deleting file...")
		dropboxFile.delete
	}
}