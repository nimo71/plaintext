package com.plaintext.adapters.json

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers._

class JSONSpec extends FlatSpec {

	val jsonString = """
		{	
			"testInt" : 1,
			"testStr" : "some-string", 
			"testArray" : [
				"first", 
				"second", 
				"third"
			]
		}
		"""

	"JSON" should "create a scala Map from a JSON string" in {
		val parsedJson = JSON.parseJSON(jsonString)

		parsedJson("testInt").toInt should be(1)
		parsedJson("testStr").toString should be("some-string")
		parsedJson("testArray")(0).toString should be("first")
		parsedJson("testArray")(1).toString should be("second")
		parsedJson("testArray")(2).toString should be("third")
	}

	"JSON" should "serialise a scala Map to a JSON string" in {
		val jsonMap = Map(
			"testInt" -> 1, 
			"testStr" -> "some-string", 
			"testArray" -> List("first", "second", "third") )

		val makeJsonString = JSON.makeJSON(jsonMap)

		makeJsonString should equal(jsonString.replaceAll("\\s", ""))
	}

}