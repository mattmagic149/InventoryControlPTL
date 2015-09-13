// JavaScript Document
var extension_obj = null;

$(document).ready(function() {
	$("#item_list").on("click", ".details", handleClickOnDetailsButton);
	$("#add_button").on("click", handleClickOnNewProductButton);
});

function handleClickOnDetailsButton(e) {
	var id = $(e.target).parent().attr("id");
	location.href = 'ProductDetail?id=' + id;
}

function handleClickOnNewProductButton(e) {
	location.href = 'ProductDetail?id=0';
}