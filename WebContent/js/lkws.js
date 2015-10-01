// JavaScript Document

$(document).ready(function() {
	$("#item_list").on("click", ".details", handleClickOnDetailsButton);
	$("#add_button").on("click", handleClickOnNewLkwButton);
	$("#show_not_active").on("click", toggleNotActiveTrucks);
});

function toggleNotActiveTrucks() {
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
	location.href = 'LkwDetail?id=' + id;
}

function handleClickOnNewLkwButton(e){
	location.href = "LkwDetail?id=0";
}