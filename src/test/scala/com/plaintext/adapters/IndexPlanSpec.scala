package com.plaintext.adapters

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._
    
class IndexPlanSpec extends FlatSpec with RunningServer {
	import dispatch._
 
 	def setup = {
		_.filter(new App)
	}
    
	"A request to index.html" should "return a 200 response" in {
		val indexReq = url("http://localhost:8080") / "index.html"
		val response = Http(indexReq)()
		assert(response.getStatusCode() === 200)
	}
	
	"A request to an unknown page" should "return a 404 response" in {
		val unknownReq = url("http://localhost:8080") / "unknown.html"
		val response = Http(unknownReq)()
		assert(response.getStatusCode() === 404)
	}
	
	"A request to index.html" should "return body content containing a link to the registration page" in {
		val indexReq = url("http://localhost:8080") / "index.html"
		val content = Http(indexReq OK as.String)()
		content should include ("<a href=\"./register.html\">")		
	}
}
