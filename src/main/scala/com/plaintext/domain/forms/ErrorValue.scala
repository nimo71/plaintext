package com.plaintext.domain.forms

class ErrorValue(val value: String, val message: String) {

	override def toString(): String = {
		"ErrorValue(value: %s, message: %s)".format(value, message)
	}

	override def equals(other: Any): Boolean = {
		other match {
			case otherError: ErrorValue => otherError.value == this.value && otherError.message == this.message
			case _ => false
		}
	}

	override def hashCode(): Int = {
		41 * (41 + value.hashCode) + message.hashCode 
	}	
}