package com.plaintext.domain

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._

class EmailSpec extends FlatSpec {

	"Email companion object" should "match a valid email string and extract the name and domain sections" in {
		val emailString = "name@domain.com"
		emailString match {
			case Email(name, domain) => {
				name should equal("name")
				domain should equal("domain.com")
			}
			case _ => fail
		}
	}
}