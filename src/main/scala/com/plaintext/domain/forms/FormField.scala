package com.plaintext.domain.forms

class FormField[T](val name: String, val value: T) {

	override def equals(other: Any): Boolean = {
		other match {
			case otherField: FormField[T] => otherField.name == this.name && otherField.value == this.value
			case _ => false
		}
	}

	override def hashCode(): Int = {
		41 * (41 + name.hashCode) + value.hashCode 
	}
}