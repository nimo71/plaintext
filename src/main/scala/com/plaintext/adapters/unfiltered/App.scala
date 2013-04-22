package com.plaintext.adapters.unfiltered


import com.plaintext.adapters.File
import com.plaintext.adapters.json._
import com.plaintext.domain.forms._
import javax.servlet.http.HttpServletResponse
import unfiltered.filter._
import unfiltered.request._
import unfiltered.response._


object RegisterResponder {
	import com.plaintext.domain.forms.FormBinding._

	def apply(json: String): ResponseFunction[HttpServletResponse] = {
		
		RegistrationFormJson.deserialize(json) match {
			case Some(form) => RegistrationForm.process(form) match {
    			    case Right(user) => {
                        //val responseJson = UserJsonSerialiser(UserRepository.createAccount(user)).serialise()
                        Ok ~> ResponseString("created User: "+ user)
    			    }
                    case Left(form) => {
                        RegistrationFormJson.serialize(form) match {
                        	case Some(json) => BadRequest ~> ResponseString(json)
                        	case _ => InternalServerError ~> ResponseString("Failed to register, please try again later")
                        }
                    }
                    case _ => InternalServerError ~> ResponseString("Failed to register, please try again later")
				}
			case _ => {
				val message = JSON.makeJSON(Map("message" -> "Expected request body"))
				BadRequest ~> ResponseString(message)
			}
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