package com.plaintext.adapters

import unfiltered.filter.Plan
import unfiltered.request._
import unfiltered.response._


class ApiPlan extends Plan {
	
	def intent = { 
		case req @ Path("/") => IndexPlan.intent(req)
		case req @ Path("/index") => IndexPlan.intent(req)
		case req @ Path("/index.html") => IndexPlan.intent(req)
	}
}
