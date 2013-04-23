package com.plaintext.adapters.unfiltered

import com.plaintext.adapters.anorm.AnormUserRepository
import com.plaintext.adapters.File
import com.plaintext.adapters.json._
import com.plaintext.domain._
import com.plaintext.domain.forms._
import javax.servlet.http.HttpServletResponse
import unfiltered.filter._
import unfiltered.request._
import unfiltered.response._

object RegisterResponder {
	import com.plaintext.domain.forms.FormBinding._

	def apply(json: String): ResponseFunction[HttpServletResponse] = {
		
		def failureResponse = InternalServerError ~> ResponseString("Failed to register, please try again later")

		def registerUser(user: User): ResponseFunction[HttpServletResponse] = {
			AnormUserRepository.createAccount(user).
				flatMap { UserJson.serialize _ }.
				map { Ok ~> ResponseString(_) }.
				getOrElse { failureResponse }
		}

		def returnInvalidForm(form: Form): ResponseFunction[HttpServletResponse] = {
			RegistrationFormJson.serialize(form).
				map { BadRequest ~> ResponseString(_) }. 
				getOrElse { failureResponse }
		}

		def returnEmptyRequestMessage(): ResponseFunction[HttpServletResponse] = {
			val message = JSON.makeJSON(Map("message" -> "Expected request body"))
			BadRequest ~> ResponseString(message)
		}

		RegistrationFormJson.deserialize(json).map { form => 
			RegistrationForm.process(form).fold(
				returnInvalidForm(_), 
				registerUser(_) )
		}. 
		getOrElse { returnEmptyRequestMessage() }
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