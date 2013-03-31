package com.plaintext.adapters

import org.clapper.avsl.Logger

object Server {
	val logger = Logger(Server.getClass)
	val http = unfiltered.jetty.Http.local(8080)

	def main(args: Array[String]) {
		http.filter( new App )
			.run({ svr =>
		  		logger.info("Starting up server")
			}, { svr =>
				logger.info("Shutting down server")
			})
	}
}