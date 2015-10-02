// JavaScript Document

$(document).ready(function() {	
	$("#search").on("keyup", handleKeyUpOnSearchField);
});

function handleKeyUpOnSearchField(e) {
	clearTimeout($.data(this, 'timer_for_search'));
	$(this).data('timer_for_search', setTimeout(search, 200));
}

function search() {
	var query_value = $('#search').val() + "";
	query_value = query_value.toLowerCase();
	console.log("Search for '" + query_value + "'");
	$(".item").addClass("hide_for_search");
	$(".item").each(function(index) {
		var childs = $(this).children();
		childs.each(function(i) {
			var current_value = $(this).text() + "";
			current_value = current_value.toLowerCase();
			if (current_value.indexOf(query_value) >= 0) {
				$(this).closest(".item").removeClass("hide_for_search");
			}
		});
	});
}