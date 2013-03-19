define(

["jquery"],  

function($) {
	
	var LoginForm = function() {
		
		this.stylesheet = $("<link rel='stylesheet' href='css/loginForm.css'>");
		$("head").append(this.stylesheet);
		
		$.ajax({ type: "GET",   
		    url: "/loginForm.html",   
		    async: false,
		    success : function(html)
		    {
		    	$("body").append(html);
		    	$(html).hide();
		    }
		});
		
		this.loginBackground = $("#loginBackground");
		this.loginForm = $("#loginForm");
		this.loginButton = $("#loginButton");
		this.cancelButton = $("#cancelButton");
		this.emailInput = $("#email");
		this.passwordInput = $("#password");
		
		this.loginForm.css({
 		    "position": "absolute",
 		    "display": "none"
 		});
		
		var that = this;
		this.cancelButton.click(function() {
			that.hide();
			return false;
		});
	}
				
	LoginForm.prototype.show = function() {	
		var loginFormTop = $("#container").height() / 2 - $("#loginForm").height() / 2;
 		var loginFormLeft = $("#container").width() / 2 - $("#loginForm").width() / 2;
 		
 		this.loginForm.css({
 		    "top": loginFormTop +"px",
 		    "left": loginFormLeft +"px"
 		});
		this.loginBackground.css({ opacity: "0.5" });
		this.loginForm.fadeIn("slow");	
	}
	
	LoginForm.prototype.hide = function() {
		var loginForm = this;
		this.loginForm.fadeOut("slow", function() {
			this.remove();
		});	
		this.loginBackground.fadeOut("slow", function() {
			this.remove();
			loginForm.stylesheet.remove();
		});
	}	
	
	LoginForm.prototype.onLogin = function(loginListener) {
		var that = this;
		function createLoginCallback(emailInput, passwordInput, listener) {
			return function() {
				if (listener) {
					listener(emailInput.val(), passwordInput.val());
				}
				return false;
			}
		}	
		this.loginButton.click( 
				createLoginCallback(
						this.emailInput, 
						this.passwordInput, 
						loginListener) );
	}
	
	return LoginForm;
});