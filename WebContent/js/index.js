// JavaScript Document

$(document).ready(function() {
	$("#login_button").on("click", logUserIn);
	$("#password").on("keyup", function(e){ if (e.which == 13) { logUserIn(); }});
	
	$(document).ajaxStart(function () { $("#loader").show(); }).ajaxStop(function () { $("#loader").hide();});
});

function logUserIn() {
	var email = $("#email").val();
	var password = $("#password").val();
		
	$.ajax({
		type: "POST",
		url: "Login",
		data: { email: email, password: password },
		cache: false,
		success: function(data, settings, xhr) {
			location.href = "Welcome";
		},
		error: function(data, settings, xhr) {
		//error: function(error, xhr, settings) {
			//if(error.status === 401) {
			createPopUp("", data.getResponseHeader('error_message'));
		}
	});
}