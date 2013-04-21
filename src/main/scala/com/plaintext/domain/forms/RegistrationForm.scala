package com.plaintext.domain.forms

import com.plaintext.domain._

object RegistrationForm {
	
	import FormBinding._

	def apply(
			email: ValidatedField[Email], 
			confirmEmail: Confirmation[Email], 
			password: ValidatedField[Password], 
			confirmPassword: Confirmation[Password]): Form = 
	{
	    new Form() :+ email :+ confirmEmail(email) :+ password :+ confirmPassword(password)
	}

	def process(form: Form): Either[Form, User] = {
		Right(new User(new Email("test@test.com"), new Password("testpassword")))
	}
}
