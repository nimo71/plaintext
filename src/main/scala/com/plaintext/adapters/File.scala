package com.plaintext.adapters

import scala.io.Source
import scala.xml.NodeSeq
import scala.xml.XML

class File(val path: String) { 

	def content(): String = { 
		val source = Source.fromFile(path, "utf-8")
		val content = source.mkString
		source.close
		content
	}
  
}
