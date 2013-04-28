package com.plaintext.domain

class Registration(val email: Email, val password: Password) {

	override def toString = "Registration:[email=%s, password=%s]".format(email, password)

	override def equals(other: Any) = {
		other match {
			case otherReg: Registration => otherReg.email == this.email && otherReg.password == this.password
			case _ => false
		}
	}

	override def hashCode = {
		41 * (41 + email.hashCode) + password.hashCode
	}
}