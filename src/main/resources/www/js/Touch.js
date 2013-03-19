define(function() {
	
	var listeners = new Array();
	
	var Touch = {
			
		addListener: function(listener) {
			listeners.push(listener);
		}, 
		
		removeListener: function(listener) {
			var index = instance.listeners.indexOf(listener)
			if (index > -1) {
				this.listeners.splice(index, 1);
			}
		} 
	}
	
	document.addEventListener('touchstart', function(e) {
		listeners.forEach(function(listener) {
			if (listener.touchstart) listener.touchstart(e);
		});
		return false;
	}, false);
	
	document.addEventListener('touchmove', function(e) {
		listeners.forEach(function(listener) {
			if (listener.touchmove) listener.touchmove(e);
		});
		return false;
	}, false);
	
	document.addEventListener('touchend', function(e) {
		listeners.forEach(function(listener) {
			if (listener.touchend) listener.touchend(e);
		});
		return false;
	}, false);
	
	document.addEventListener('touchcancel', function(e) {
		listeners.forEach(function(listener) {
			if (listener.touchcancel) listener.touchcancel(e);
		});
		return false;
	}, false);
	
	return Touch;
});
	