package com.plaintext.adapters

import org.scalatest._

trait RunningServer extends SequentialNestedSuiteExecution { this: Suite => 
	import unfiltered.jetty._
	
	val host = Http.local(8080)
	
	def setup: (Server => Server) 
    
	lazy val server = setup(host)
	
	abstract override def withFixture(test: NoArgTest) {
		server.start()
		try {
			super.withFixture(test)
			
		}
		finally {
			println("about to stop server")
			Thread.sleep(200)
			server.stop()
			server.destroy()
		}	
	}
	
}
    
class IndexPlanSpec extends FlatSpec with RunningServer {
	import dispatch._
 
 	def setup = {
		println("in setup")
		_.filter(new App)
	}
    
	"A request to index.html" should "return a 200 response" in {
		val indexReq = url("http://localhost:8080") / "index.html"
	
		println("about to request")
		for (str <- Http(indexReq OK as.String)) {
			println(str)			
		}		
		val status = 404
		assert(status === 200)
	}
}
