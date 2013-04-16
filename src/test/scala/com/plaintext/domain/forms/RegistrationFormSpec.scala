package com.plaintext.domain.forms

import com.plaintext.domain._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._

class RegistrationFormSpec extends FlatSpec {

	"RegistrationForm object" should "create a registration form from a json string" in {
		
		val registrationJson = """{
				"email" : "test@test.com", 
				"confirmEmail" : "test@test.com", 
				"password" : "testpassword", 
				"confirmPassword" : "testpassword"
			}"""

		val registrationForm = RegistrationForm(registrationJson)

		registrationForm should equal(
			new Form(
				new FormField[Email]("email", new Email("test@test.com")), 
				new FormField[String]("confirmEmail", "test@test.com"), 
				new FormField[Password]("password", new Password("testpassword")), 
				new FormField[String]("confirmPassword", "testpassword") ) )
	}
}