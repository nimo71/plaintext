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

	def process(form: Form): Either[Form, Registration] = {
		val fields = form.asMap
		(fields("email"), fields("confirmEmail"), fields("password"), fields("confirmPassword")) match {
			case (email: Email, confirmEmail: String, password: Password, confirmPassword: String) => Right(new Registration(email, password))
			case _ => Left(form)
		}
	}
}
