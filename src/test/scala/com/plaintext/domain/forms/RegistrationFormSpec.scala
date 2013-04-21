package com.plaintext.domain.forms

import com.plaintext.domain._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._

class RegistrationFormSpec extends FlatSpec {

	import com.plaintext.domain.forms.FormBinding._

	"RegistrationForm object" should "create a registration form from string parameters" in {

		val registrationForm = RegistrationForm("test@test.com", "test@test.com", "testpassword", "testpassword")

		registrationForm should equal(
			new Form(
				new FormField("email", new Email("test@test.com")), 
				new FormField("confirmEmail", "test@test.com"), 
				new FormField("password", new Password("testpassword")), 
				new FormField("confirmPassword", "testpassword") ) )
	}

	"RegistrationForm object" should "create a registration form with errors for a badly formatted email" in {
		val registrationForm = RegistrationForm("testtest.com", "test@test.com", "testpassword", "testpassword")

		registrationForm should equal(
			new Form(
				new FormField("email", new ErrorValue("testtest.com", "Invalid email address")), 
				new FormField("confirmEmail", "test@test.com"), 
				new FormField("password", new Password("testpassword")), 
				new FormField("confirmPassword", "testpassword") ) )
	}

	"RegistrationForm object" should "create a registration form with errors for an incorrect email confirmation" in {
		val registrationForm = RegistrationForm("test@test.com", "test@tests.com", "testpassword", "testpassword")

		registrationForm should equal(
			new Form(
				new FormField("email", new Email("test@test.com")), 
				new FormField("confirmEmail", new ErrorValue("test@tests.com", "Email must match confirmation")), 
				new FormField("password", new Password("testpassword")), 
				new FormField("confirmPassword", "testpassword") ) )	
	}

	"RegistrationForm object" should "create a registration form with errors for an incorrect password" in {

		val registrationForm = RegistrationForm("test@test.com", "test@test.com", "t", "testpassword")

		registrationForm should equal(
			new Form(
				new FormField("email", new Email("test@test.com")), 
				new FormField("confirmEmail", "test@test.com"), 
				new FormField("password", new ErrorValue("t", "Password must have at least 6 characters")), 
				new FormField("confirmPassword", "testpassword") ) )
	}

	"RegistrationForm object" should "create a registration form with errors for an incorrect password confirmation" in {

		val registrationForm = RegistrationForm("test@test.com", "test@test.com", "testpassword", "nomatch")

		registrationForm should equal(
			new Form(
				new FormField("email", new Email("test@test.com")), 
				new FormField("confirmEmail", "test@test.com"), 
				new FormField("password", new Password("testpassword")), 
				new FormField("confirmPassword", new ErrorValue("nomatch", "Password must match confirmation")) ) )
	}

	"Processing a valid RegistrationForm" should "produce a User" in {
		val form = RegistrationForm("test@test.com", "test@test.com", "testpassword", "testpassword")

		RegistrationForm.process(form) match {
			case Right(user) => user should equal(new User(new Email("test@test.com"), new Password("testpassword")))
		}
	}
}