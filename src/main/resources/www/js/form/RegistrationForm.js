define(
		
["jquery"],

function($) {
		
	var RegistrationForm = function() {
			
		this.stylesheet = $("<link rel='stylesheet' href='css/registrationForm.css'>");
		$("head").append(this.stylesheet);
		
		$.ajax({ type: "GET",   
		     url: "/registrationForm.html",   
		     async: false,
		     success : function(html)
		     {
		    	 $("body").append(html);
		    	 $(html).hide();
		     }
		});
		
		this.background = $("#background");
		this.regForm = $("#registrationForm");
		this.registerButton = $("#registerButton");
		this.cancelButton = $("#cancelButton");
		this.emailInput = $("#email");
		this.confirmEmailInput = $("#confirmEmail");
		this.passwordInput = $("#password");
		this.confirmPasswordInput = $("#confirmPassword");
		
		this.regForm.css({
		    "position": "absolute",
		    "display": "none"
		});
		
		var that = this;
		this.cancelButton.click(function() {
			that.hide();
			return false;
		});
	}
				
	RegistrationForm.prototype.show = function() {
		var regFormTop = $("#container").height() / 2 - $("#registrationForm").height() / 2;
		var regFormLeft = $("#container").width() / 2 - $("#registrationForm").width() / 2;
		
		this.regForm.css({
		    "top": regFormTop +"px",
		    "left": regFormLeft +"px"
		});
		this.background.css({ opacity : "0.5" });
		this.regForm.fadeIn("slow");	
	}
	
	RegistrationForm.prototype.hide = function() {
		var regForm = this;
		this.regForm.fadeOut("slow", function() {
			this.remove();
		});	
		this.background.fadeOut("slow", function() {
			this.remove();
			regForm.stylesheet.remove();
		});
	}	
	
	RegistrationForm.prototype.onRegister = function(registerListener) {
		var that = this;
		function createCallback(emailInput, confirmEmailInput, passwordInput, confirmPasswordInput, listener) {
			return function() {
				if (listener) {
					listener(emailInput.val(), confirmEmailInput.val(), passwordInput.val(), confirmPasswordInput.val());
				}
				return false;
			}
		}	
		this.registerButton.click( 
				createCallback(this.emailInput, 
						this.confirmEmailInput, 
						this.passwordInput, 
						this.confirmPasswordInput, 
						registerListener) );
	}
	
	return RegistrationForm;
		
});