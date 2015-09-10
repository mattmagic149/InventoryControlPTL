// JavaScript Document

$(document).ready(function() {
	createAjaxLoader();
	$(document).ajaxStart(showAjaxLoader).ajaxStop(hideAjaxLoader);
});

function showAjaxLoader() { 
	$("#loader").show(); 
}

function hideAjaxLoader() {
	$("#loader").hide();
}

function createAjaxLoader() {
	$("#wrapper").append("<div id='loader'>" +
			"<img src='images/ajax-loader.gif' /></div>");
}
