// JavaScript Document
var extension_obj = null;

$(document).ready(function() {
	$("#item_list").on("click", ".details", handleClickOnDetailsButton);
	$("#add_button").on("click", handleClickOnNewProductButton);
	$("#show_not_active").on("click", toggleNotActiveProducts);
	
	/*formatting width of elements in list*/
	if ($("#item_header").children().length == 3) {
		$(".name").width("32%");
	}
});

function toggleNotActiveProducts() {
	if ($("#show_not_active").text() == "Inaktive anzeigen") { //show inactive & hide active
		$("#show_not_active").text("Aktive anzeigen");
		$(".not_active").removeClass("hidden");
		$(".active").addClass("hidden");
	} else { //show active & hide inactive
		$("#show_not_active").text("Inaktive anzeigen");
		$(".not_active").addClass("hidden");
		$(".active").removeClass("hidden");
	}
}

function handleClickOnDetailsButton(e) {
	var id = $(e.target).parent().attr("id");
	location.href = 'ProductDetail?id=' + id;
}

function handleClickOnNewProductButton(e) {
	location.href = 'ProductDetail?id=0';
}