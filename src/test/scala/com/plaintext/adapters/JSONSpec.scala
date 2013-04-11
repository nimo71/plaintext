package com.plaintext.adapters

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._

class JSONSpec extends FlatSpec {

	"Json" should "create a scala Map from a JSON string" in {
		val jsonString = """
		{	
			"testInt" : 1,
			"testStr" : "some string", 
			"testArray" : [
				"first", 
				"second", 
				"third"
			]
		}
		"""
		val parsedJson = JSON.parseJSON(jsonString)

		parsedJson("testInt").toInt should be(1)
		parsedJson("testStr").toString should be("some string")
		parsedJson("testArray")(0).toString should be("first")
		parsedJson("testArray")(1).toString should be("second")
		parsedJson("testArray")(2).toString should be("third")
	}

}