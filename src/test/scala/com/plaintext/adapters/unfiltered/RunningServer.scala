package com.plaintext.adapters.unfiltered

import unfiltered.jetty._
import org.scalatest._

trait RunningServer extends BeforeAndAfterAll { this: Suite => 
	
	val host = Http.local(8080)
	
	def setup: (Server => Server) 
    
	lazy val server = setup(host)
	
	override def beforeAll() {
		server.start()
	}
	
	override def afterAll() {
		Thread.sleep(100)
		server.stop()
		server.destroy()
	}
}