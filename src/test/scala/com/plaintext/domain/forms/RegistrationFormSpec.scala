package com.plaintext.domain.forms

import com.plaintext.domain._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._

class RegistrationFormSpec extends FlatSpec {

	"RegistrationForm object" should "creates a registration form from a json string" in {
		
		val registrationJson = """{
				"email" : "test@test.com", 
				"confirmEmail" : "test@test.com" 
			}"""

		val registrationForm = RegistrationForm(registrationJson)

		registrationForm should equal(
			new Form(
				new FormField[Email]("email", "test@test.com"), 
				new FormField[String]("confirmEmail", "test@test.com") ) )
	}
}