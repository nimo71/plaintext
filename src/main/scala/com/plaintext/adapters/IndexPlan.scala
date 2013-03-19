package com.plaintext.adapters

import unfiltered.response._
import unfiltered.filter.Plan

object IndexPlan extends unfiltered.filter.Plan {

	def intent = { 
		case _ => Ok ~> ResponseString(new File("src/main/resources/www/index.html").content)
	}

}