package com.plaintext.domain.forms

class Form(val fields: FormField[_] *) {

	import FormBinding._

	def asMap() = fields.foldLeft(Map.empty[String, Any])((map: Map[String, Any], field: FormField[_]) => {
		map + (field.name -> field.value)
	})

	def :+ (field: FormField[_]): Form = {
		new Form((fields :+ field):_*)
	}

	def :+ (validated: ValidatedField[_]): Form = {
		validated.fold(this :+ _, this :+ _)
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
