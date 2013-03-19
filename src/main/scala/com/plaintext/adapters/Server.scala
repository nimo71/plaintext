package com.plaintext.adapters

import unfiltered.request._
import unfiltered.response._
import org.clapper.avsl.Logger
import unfiltered.jetty.ContextBuilder
import unfiltered.filter.Planify

object Server {
	val logger = Logger(Server.getClass)
	val http = unfiltered.jetty.Http.local(8080)

	def main(args: Array[String]) {
		http
			.filter( Planify {
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
			})
//			.context("/") { ctx: ContextBuilder =>
//			  	ctx.resources(getClass().getResource("/www"))
//			}
			.run({ svr =>
		  		logger.info("Starting up server")
			}, { svr =>
				logger.info("Shutting down server")
			})
	}
}