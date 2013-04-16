package com.plaintext.domain

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