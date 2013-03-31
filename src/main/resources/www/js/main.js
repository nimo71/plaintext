require([
     "jquery", 
     "form/LoginForm", 
     "form/RegistrationForm",
],     
function($, LoginForm, RegistrationForm) {
	
	function showLoginForm() {
		var loginForm = new LoginForm();
		loginForm.onLogin(function(email, password) {
			alert("Logging in with: email="+ email +" and password="+ password +".");
			loginForm.hide();
		});
		loginForm.show();
	}
	
	function showRegistrationForm() {		
		var registrationForm = new RegistrationForm();
		registrationForm.onRegister(function(email, confirmEmail, password, confirmPassword) {
			alert("Registering with: email=" + email + ", confirmEmail=" + confirmEmail + ", password=" + password + ", and confirmPassword=" + confirmPassword);
			registrationForm.hide();
		});	
		registrationForm.show();
	}
	
	$("#loginLink").click(showLoginForm);
	$("#registerLink").click(showRegistrationForm);
});