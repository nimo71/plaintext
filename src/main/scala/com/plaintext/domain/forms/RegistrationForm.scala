package com.plaintext.domain.forms

import com.plaintext.domain._
import com.plaintext.adapters.JSON

object RegistrationForm {
	
	def apply(json: String): Form = {
		val tree = JSON.parseJSON(json)
		
		val email = tree("email").toString
		val confirmEmail = tree("confirmEmail").toString
		val password = tree("password").toString
		val confirmPassword = tree("confirmPassword").toString

		new Form(
			new FormField[Email]("email", new Email(email)), 
			new FormField[String]("confirmEmail", confirmEmail), 
			new FormField[Password]("password", new Password(password)), 
			new FormField[String]("confirmPassword", confirmPassword))
	}
}
