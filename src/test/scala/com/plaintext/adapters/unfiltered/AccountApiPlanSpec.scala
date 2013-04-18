package  com.plaintext.adapters.unfiltered

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
	}

	"A request to put account with no JSON body" should "return 400 (Bad Request)" in {
		val registrationReq = putAccount.setHeader("Content-type", "application/json")
		val response = Http(registrationReq)()
		assert(response.getStatusCode === 400)
		response.getResponseBody() should fullyMatch regex """\{\s*"error" : \{\s*"message" : "Expected request body"\s*\}\s*\}"""
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
		response.getResponseBody() should fullyMatch regex """\{\s*"errors" : \[\{\s*"name" : "confirmEmail",\s*"message" : "confirmEmail does not match email"\s*\}\s*\}\]"""
	}

}