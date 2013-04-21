package com.plaintext.domain.forms

import com.plaintext.domain._

object RegistrationForm {
	
	type ValidatedField[T] = Either[FormField[ErrorValue], FormField[T]]
	
	type Confirmation[T] = ValidatedField[T] => ValidatedField[String]

	implicit def stringToEmailFieldValidator(value: String): ValidatedField[Email] = {
		value match {
			case Email(_, _) => Right(new FormField[Email]("email", new Email(value)))
			case _ => Left(new FormField[ErrorValue]("email", new ErrorValue(value, "Invalid email address")))
		}
	}

	implicit def stringToEmailConfirmation(confirmEmail: String): Confirmation[Email] = {
		
		val validConfirmEmail = Right(new FormField("confirmEmail", confirmEmail))
		val invalidConfirmEmail = Left(new FormField("confirmEmail", new ErrorValue(confirmEmail, "Email must match confirmation")))
		
		val validateEmailConfirmation = (validatedEmail: ValidatedField[Email]) => {
			validatedEmail match {
				case Right(FormField(_, email: Email)) => 
						if (email.value == confirmEmail) 
							validConfirmEmail 
						else 
							invalidConfirmEmail

				case _ => validConfirmEmail
			}
		}

		validateEmailConfirmation
	}

	implicit def stringToPassword(value: String): Password = {
		new Password(value)
	}

	def apply(
			email: ValidatedField[Email], 
			confirmEmail: Confirmation[Email], 
			password: String, 
			confirmPassword: String): Form = {

	    val fields = email :: confirmEmail(email) :: Nil 

	    val form = fields.foldLeft(new Form())(
	    				(form, validated) => { validated.fold(form.add(_), form.add(_)) } 
	    			)

		form.add(new FormField[Password]("password", password))
			.add(new FormField[String]("confirmPassword", confirmPassword))
	}
}
