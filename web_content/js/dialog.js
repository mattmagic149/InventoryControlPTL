// JavaScript Document

$(document).ready(function() {

	$("body").on("click", "#overlay", closeAddingInput);
	$("body").on("click", "#close", closeAddingInput);
	$("body").on("click", "#close", closeAddingInput);
	
});

//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
//Creates the POP-UP
//
function createPopUp(headline, message) {
	$("#wrapper").append("<div id='pop_up'>" +
			"<img src='images/close-icon.png' id='close' alt='close' title='close' />" +
			"<h2 id='pop_up_headline'>" + headline + "</h2>" +
			"<p id='pop_up_message'>" + message + "</p>" +
			"</div><div id='overlay'></div>");
	
	var duration = 300;
	$("#overlay").fadeIn(duration);
	$("#pop_up").slideDown(duration);
	$("#close").delay(duration).fadeIn(200);
}

function createPopUpButtons(button_1, button_2) {
	$("#pop_up_message").append("<section id='confirm_pop_up_button' class='pop_up_button'>" + button_1 + "</section>" +
			"<section id='cancel_pop_up_button' class='pop_up_button'>" + button_2 + "</section>");
}

//--------------------------------------------------------------------------------
//--------------------------------------------------------------------------------
//Closes the Input Container
//
function closeAddingInput() {
	var input = $("#pop_up");
	var overlay = $("#overlay");
	var duration = 300;
	
	$("#close").fadeOut(200);
	input.slideUp(duration);
	overlay.fadeOut(duration);
	
	window.setTimeout(function() {input.remove();}, duration);
	window.setTimeout(function() {overlay.remove();}, duration);
}
