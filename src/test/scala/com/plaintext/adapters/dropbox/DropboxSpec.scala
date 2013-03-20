package com.plaintext.adapters.dropbox

import org.scalatest.FlatSpec
import java.util.Properties

class DropboxSpec extends FlatSpec {

	val props = new Properties()
	props.load(this.getClass().getClassLoader().getResourceAsStream("dropbox.properties"))
	
	"Dropbox" should "initialise with an app key and secret key" in {
		val appKey = props.get("app.key").asInstanceOf[String]
		val secretKey = props.get("secret.key").asInstanceOf[String]

		val dropbox = new Dropbox(appKey, secretKey)
	}
}