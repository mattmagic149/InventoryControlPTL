// JavaScript Document
var extension_obj = null;

$(document).ready(function() {
	$("#item_list").on("click", ".details", handleClickOnDetailsButton);
	$("#add_button").on("click", handleClickOnNewProductButton);
	
	/*formatting width of elements in list*/
	if ($("#item_header").children().length == 3) {
		$(".name").width("32%");
	}
});

function handleClickOnDetailsButton(e) {
	var id = $(e.target).parent().attr("id");
	location.href = 'ProductDetail?id=' + id;
}

function handleClickOnNewProductButton(e) {
	location.href = 'ProductDetail?id=0';
}