package com.plaintext.domain.forms

import com.plaintext.domain._

object FormBinding {
	
	type ValidatedField[T] = Either[FormField[ErrorValue], FormField[T]]

	type Confirmation[T] = ValidatedField[T] => ValidatedField[String]

	implicit def stringToEmailFieldValidator(value: String): ValidatedField[Email] = {
		value match {
			case Email(_, _) => Right(new FormField[Email]("email", new Email(value)))
			case _ => Left(new FormField[ErrorValue]("email", new ErrorValue(value, "Invalid email address")))
		}
	}

	implicit def stringToPasswordFieldValidator(value: String): ValidatedField[Password] = {
		if (value.size < 6)
			Left(new FormField("password", new ErrorValue(value, "Password must have at least 6 characters")))
		else
			Right(new FormField("password", new Password(value)))
	}

	def fieldConfirmation[T](confirmValue: String)
			(fieldName: String, 
				matcher: (T, String) => Boolean, 
				errorMessage: String ): Confirmation[T] = 
	{
		val validConfirm = Right(new FormField(fieldName, confirmValue))
		val invalidConfirm = Left(new FormField(fieldName, new ErrorValue(confirmValue, errorMessage)))

		val validateConfirmation = (validatedValue: ValidatedField[T]) => {
			validatedValue match {
				case Right(FormField(_, value: T)) => 
					if (matcher(value, confirmValue))
						validConfirm
					else
						invalidConfirm

				case _ => validConfirm
			}
		}

		validateConfirmation
	}

	implicit def stringToEmailConfirmation(confirmEmail: String) = 
		fieldConfirmation[Email](confirmEmail)("confirmEmail", (email, confirm) => { email.value == confirm }, "Email must match confirmation")

	implicit def stringToPasswordConfirmation(confirmPassword: String) = 
		fieldConfirmation[Password](confirmPassword)("confirmPassword", (password, confirm) => { password.value == confirm }, "Password must match confirmation")
}
