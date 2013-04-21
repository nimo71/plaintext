package com.plaintext.domain.forms


object FormField {

	type ValidatedField[T] = Either[FormField[ErrorValue], FormField[T]]

	type Confirmation[T] = ValidatedField[T] => ValidatedField[String]

	def unapply[T](field: FormField[T]): Option[(String, T)] = {
		Some((field.name, field.value))
	}
}

class FormField[T](val name: String, val value: T)
{
	override def toString(): String = {
		"FormField(name: "+ name +", value: "+ value +")"
	}

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
