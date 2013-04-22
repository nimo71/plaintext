package com.plaintext.adapters.unfiltered

import com.plaintext.adapters.json.JSON
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._
import dispatch._
import com.ning.http.client.RequestBuilder

class AccountApiPlanSpec extends FlatSpec with RunningServer {
 
 	def setup = {
		_.filter(App)
	}
    
	def putAccount = url("http://localhost:8080").PUT / "api" / "account"

	"A request to put account with valid JSON" should "return a 200 response" in {

		val body = """
				{
					"email" : "test@test.com",
					"confirmEmail" : "test@test.com", 
					"password" : "somepassword",
					"confirmPassword" : "somepassword"
				}
			"""

		val registrationReq = putAccount
				.setBody(body)
				.setHeader("Content-type", "application/json")

		val response = Http(registrationReq)()

		assert(response.getStatusCode === 200)

		val parsedJson = JSON.parseJSON(response.getResponseBody())
		parsedJson("email").toString should equal("test@test.com")
		parsedJson("password").toString should equal("somepassword")
	}

	"A request to put account with no JSON body" should "return 400 (Bad Request)" in {
		val registrationReq = putAccount.setHeader("Content-type", "application/json")
		val response = Http(registrationReq)()
		assert(response.getStatusCode === 400)
		val body = response.getResponseBody() 
		JSON.parseJSON(body)("message").toString should equal("Expected request body")
	}

	"A request to put account with different email and confirmEmail fields" should "return 400 (Bad Request)" in {
		val body = """
				{
					"email" : "test@test.com",
					"confirmEmail" : "doesn't match", 
					"password" : "somepassword",
					"confirmPassword" : "somepassword"
				}
			"""

		val registrationReq = putAccount
				.setBody(body)
				.setHeader("Content-type", "application/json")

		val response = Http(registrationReq)()
		assert(response.getStatusCode === 400)

		val parsedJSON = JSON.parseJSON(response.getResponseBody())
		parsedJSON("email").toString should equal("test@test.com")
		parsedJSON("confirmEmail")("value").toString should equal("doesn't match")
		parsedJSON("confirmEmail")("message").toString should equal("Email must match confirmation")
		parsedJSON("password").toString should equal("somepassword")
		parsedJSON("confirmPassword").toString should equal("somepassword")
	}
}