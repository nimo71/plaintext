package com.plaintext.adapters.json

import com.plaintext.domain._

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._

class UserJsonSpec extends FlatSpec {

    "UserJson" should "serialize user objects" in {
        val userJson = UserJson.serialize(new User(123, new Email("test@test.com"), new Password("somepassword")))
        userJson.get should be("""{"email":"test@test.com","password":"somepassword"}""")
    }
}