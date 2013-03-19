define (
		
function() {
	
	var List = function(head, tail) {
		this._head = head; 
		this._tail = tail; 
	}

	List.empty = function() {
		return new List(undefined, undefined);
	}
	
	List.prototype.head = function() { 
		return this._head; 
	}
	
	List.prototype.tail = function() {
		if (!this._tail || this._tail.isEmpty()) {
			return List.empty();
		}
		if (this._tail.tail().isEmpty()) {
			return new List(this._tail.head(), List.empty());
		}
		return this._tail;
	}
	
	List.prototype.cons = function(val) {
		if (this.isEmpty()) {
			return new List(val, List.empty())
		}
		return new List(val, this._tail.cons(this._head) );
	}
	
	List.prototype.consAll = function(vals) { 
		var thisList = this;
		vals.foreach(function (val) {
			thisList = thisList.cons(val);
		});
		return thisList;
	}
	
	List.prototype.isEmpty = function() {
		return !this._head && !this._tail;
	}
	
	List.prototype.foreach = function(fn) {
		if (this.isEmpty()) {
			return;
		}
		fn(this._head);
		this._tail.foreach(fn);
	}
	
	List.prototype.remove = function(val) {
		if (this.head() === val) {
			return this.tail();
		}
		else {
			this._tail = this.tail().remove(val);
			return this;
		}
	}
	
	List.prototype.filter = function(matches) {
		if (this.isEmpty()) {
			return List.empty();
		}
		if (matches(this.head())) {
			return new List(this.head(), this.tail().filter(matches));
		}
		return this.tail().filter(matches);
	}

	List.prototype.foldLeft = function(acc, fn) {
		var accumulation = acc;
		if (this.isEmpty()) {
			return accumulation;
		}
		accumulation = fn(acc, this.head());
		return this.tail().foldLeft(accumulation, fn);
	}
	
	return List; 
	
}); 