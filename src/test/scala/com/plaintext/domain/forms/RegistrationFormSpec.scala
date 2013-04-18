package com.plaintext.domain.forms

import com.plaintext.domain._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._

class RegistrationFormSpec extends FlatSpec {

	"RegistrationForm object" should "create a registration form from a json string" in {

		val registrationForm = RegistrationForm("test@test.com", "test@test.com", "testpassword", "testpassword")

		registrationForm should equal(
			new Form(
				new FormField[Email]("email", new Email("test@test.com")), 
				new FormField[String]("confirmEmail", "test@test.com"), 
				new FormField[Password]("password", new Password("testpassword")), 
				new FormField[String]("confirmPassword", "testpassword") ) )
	}
}