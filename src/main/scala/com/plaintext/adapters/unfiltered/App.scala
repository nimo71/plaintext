package com.plaintext.adapters.unfiltered


import com.plaintext.adapters.File
import com.plaintext.adapters.JSON
import com.plaintext.domain.forms.RegistrationForm
import javax.servlet.http.HttpServletResponse
import unfiltered.filter._
import unfiltered.request._
import unfiltered.response._


object RegisterResponder {
	def apply(json: String): ResponseFunction[HttpServletResponse] = {
		if (json == null || json == "") 
				BadRequest ~> ResponseString("""{ 
						"error" : {
							"message" : "Expected request body"
						}
					}""")
			else {
				val tree = JSON.parseJSON(json)		
				val email = tree("email").toString
				val confirmEmail = tree("confirmEmail").toString
				val password = tree("password").toString
				val confirmPassword = tree("confirmPassword").toString

				RegistrationForm(email, confirmEmail, password, confirmPassword)

				Ok ~> ResponseString("success")
			}
	}
}

object Api {

	def intent = Intent {
		case req @ PUT(Path(Seg("api" :: "account" :: _))) => {// & Accepts.Json(r)) => {
			RegisterResponder(Body.string(req))
		}
		case _ => Pass
	}
}

object Static {
	
	def intent = Intent {
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