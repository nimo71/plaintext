package com.plaintext.adapters

import unfiltered.request._
import unfiltered.response._

object Api {
	//import unfiltered.request.{Path => UFPath}

	def intent = unfiltered.filter.Intent {
		case req @ PUT(Path(Seg("api" :: "account" :: _))) => {// & Accepts.Json(r)) => {
			val content = Body.string(req)
			if (content == null || content == "") 
				BadRequest ~> ResponseString("""{ 
						"error" : {
							"message" : "Expected request body"
						}
					}""")
			else
				Ok ~> ResponseString("success")
		}
		case _ => Pass
	}
}

object Static {
	def intent = unfiltered.filter.Intent {
  		case Path(p) => {
			try {
				val responseContent = new File("src/main/resources/www/%s".format(p)).content
				val Filename = """(.*)[.]([^.]*)""".r
				p match {
					case Filename(_, "html") => Ok ~> HtmlContent ~> ResponseString(responseContent)
					case Filename(_, "css") => Ok ~> CssContent ~> ResponseString(responseContent)
					case Filename(_, "js") => Ok ~> JsContent ~> ResponseString(responseContent) 
					case _ => Ok ~> ResponseString(responseContent)
				}
			}
			catch {
		  		case _ => NotFound ~> ResponseString(new File("src/main/resources/www/404.html").content)
			}
  		}
	}
}

object App extends unfiltered.filter.Plan {
	def intent = Api.intent.onPass(Static.intent)
}