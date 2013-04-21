package com.plaintext.domain

object Email {

	def unapply(email: String): Option[(String, String)] = {
		val parts = email split "@"
      	if (parts.length == 2) Some(parts(0), parts(1)) else None
	}

}

class Email(val value: String) {
		
	override def equals(other: Any): Boolean = {
		other match {
			case otherEmail: Email => otherEmail.value == value
			case _ => false
		}
	}

	override def hashCode(): Int = {
		value.hashCode
	}
}
