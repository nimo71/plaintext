package com.plaintext.domain.forms

import com.plaintext.domain._
import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._

class FormSpec extends FlatSpec {

    "Form asMap" should "return field name and values as a map" in {
        new Form(
        	new FormField("email", new Email("test@test.com")), 
        	new FormField("password", new Password("password"))
        ).asMap should equal(Map(
        	"email" -> new Email("test@test.com"), 
        	"password" -> new Password("password") ) )
    }
}