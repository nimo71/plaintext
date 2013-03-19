define(
function(List) {
	
	var HashMap = function() {
		this._buckets = {};
	}
	
	HashMap.prototype.put = function(key, value) {
		this._buckets[key.hashCode()] = value;
	}
	
	HashMap.prototype.value = function(key) {
		return this._buckets[key.hashCode()];
	}

	HashMap.prototype.containsKey = function(key) {
		return (this._buckets[key.hashCode()]) ? true : false;
	}
	
	return HashMap;

});