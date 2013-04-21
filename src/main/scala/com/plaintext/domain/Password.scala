package com.plaintext.domain

class Password(val value: String) {

	override def toString() = "Password(\"%s\")".format(value)

	override def equals(other: Any): Boolean = {
		other match {
			case otherPassword: Password => otherPassword.value == value
			case _ => false
		}
	}

	override def hashCode(): Int = {
		value.hashCode	
	} 
}
