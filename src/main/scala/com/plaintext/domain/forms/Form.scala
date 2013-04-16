package com.plaintext.domain.forms

class Form(val fields: FormField[_] *) {

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
