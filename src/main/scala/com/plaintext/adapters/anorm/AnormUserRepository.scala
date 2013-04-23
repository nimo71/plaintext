package com.plaintext.adapters.anorm

import com.plaintext.domain.User

object AnormUserRepository {

	def createAccount(user: User): Option[User] = {
		Some(user)
	}
	
}
