package com.plaintext.adapters.json

import com.plaintext.domain._
import com.plaintext.domain.forms._

object RegistrationFormJson {

	import JSON._

	def serialize(form: Form): Option[String] = {
		try {
			val jsonMap = form.asMap.foldLeft(Map.empty[String, Any]) {
				case (acc, (k, v)) => 
					v match {
						case error: ErrorValue => acc + (k -> Map("value" -> error.value, "message" -> error.message))
						case email: Email => acc + (k -> email.value)
						case password: Password => acc + (k -> password.value)
						case confirm: String => acc + (k -> confirm)
						case _ => throw new RegistrationFormException
					}
			}
			Some(makeJSON(jsonMap))
		}
		catch {
			case _ => None
		}
	}

	case class RegistrationFormException extends Exception
}