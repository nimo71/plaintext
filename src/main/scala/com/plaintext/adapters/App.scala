package com.plaintext.adapters

import unfiltered.request._
import unfiltered.response._
//import unfiltered.jetty.ContextBuilder

class App extends unfiltered.filter.Plan {
	def intent = {
  		case Path(Seg("api"::_)) => Ok ~> ResponseString("api call")
  		case Path(p) => {
			try {
				val responseContent = new File("src/main/resources/www/%s".format(p)).content
				p.split("""\.""") match {
					case Array(_, "html") => Ok ~> HtmlContent ~> ResponseString(responseContent)
					case Array(_, "css") => Ok ~> CssContent ~> ResponseString(responseContent) 
					case Array(_, "js") => Ok ~> JsContent ~> ResponseString(responseContent) 
					case _ => Ok ~> ResponseString(responseContent)
				}
			}
			catch {
		  		case _ => NotFound ~> ResponseString(new File("src/main/resources/www/404.html").content)
			}
  		}
	}
}