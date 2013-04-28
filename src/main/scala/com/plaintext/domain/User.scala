package com.plaintext.domain

case class User(val id: Int, val email: Email, val password: Password) {

	override def toString = "User(id: %s, email: %s, password: %s)".format(id, email, password)

	override def equals(other: Any) = {
		other match {
			case otherUser: User => 
				otherUser.id == this.id &&
				otherUser.email == this.email && 
				otherUser.password == this.password
				
			case _ => false
		}
	}

	override def hashCode = {
		41 * (41 + email.hashCode) + password.hashCode 
	}
}