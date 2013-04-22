package com.plaintext.adapters.json

import com.plaintext.domain.User
import JSON._

object UserJson {

	def serialize(user: User): Option[String] = {
		Some(makeJSON(Map("email" -> user.email.value, "password" -> user.password.value)))
	}

}