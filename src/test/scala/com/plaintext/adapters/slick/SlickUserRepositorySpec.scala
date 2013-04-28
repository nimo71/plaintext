package com.plaintext.adapters.slick

import com.plaintext.domain._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._

class SlickUserRepositorySpec extends FlatSpec {

    "SlickUserRepository" should "insert user objects into the database" in {
    	val email = new Email("test@test.com")
    	val password = new Password("password")

        SlickUserRepository.createAccount(email, password) map { u => 
        	u.id should be > (0)
        	u.email should be (email)
        	u.password should be (password)
        } orElse {
        	fail()
        }
        
    }
}