// JavaScript Document

$(document).ready(function() {
	$("#item_list").on("click", ".details", handleClickOnDetailsButton);
	$("#add_button").on("click", handleClickOnNewLkwButton);
});

function handleClickOnDetailsButton(e) {
	var id = $(e.target).parent().attr("id");
	location.href = 'LkwDetail?id=' + id;
}

function handleClickOnNewLkwButton(e){
	alert("handleClickOnNewLkwButton");
}