package com.plaintext.domain.forms

import com.plaintext.domain._

object RegistrationForm {
	
	implicit def stringToEmail(value: String): Email = new Email(value)
	implicit def stringToPassword(value: String): Password = new Password(value)

	def apply(email: String, confirmEmail: String, password: String, confirmPassword: String): Form = {
		new Form(
			new FormField[Email]("email", email), 
			new FormField[String]("confirmEmail", confirmEmail), 
			new FormField[Password]("password", password), 
			new FormField[String]("confirmPassword", confirmPassword))
	}
}
