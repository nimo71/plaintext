package com.plaintext.domain

class User(val email: Email, val password: Password) {

	override def toString = "User(email: %s, password: %s)".format(email, password)

	override def equals(other: Any) = {
		other match {
			case otherUser: User => otherUser.email == this.email && otherUser.password == this.password
			case _ => false
		}
	}

	override def hashCode = {
		41 * (41 + email.hashCode) + password.hashCode 
	}
}