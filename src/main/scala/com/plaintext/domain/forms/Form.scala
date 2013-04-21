package com.plaintext.domain.forms

class Form(val fields: FormField[_] *) {

	def add(field: FormField[_]): Form = {
		new Form((fields :+ field):_*)
	}

	override def toString(): String = {
		"Form(fields: "+ fields.toString +")"
	}

	override def equals(other: Any): Boolean = {
		other match {
			case otherForm: Form => fields.equals(otherForm.fields)
			case _ => false
		}
	}

	override def hashCode(): Int = {
		fields.hashCode
	}
}
