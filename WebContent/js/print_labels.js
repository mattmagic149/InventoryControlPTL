// JavaScript Document

$(document).ready(function() {	
	$("#wrapper").on("click", "#ok", printSelectedLabels);
});


var barcode_options = {
					width:  2,
					height: 100,
					quite: 0,
					format: "CODE128",
					displayValue: true,
					font:"monospace",
					textAlign:"center",
					fontSize: 17,
					backgroundColor:"",
					lineColor:"#000"
				};

function printSelectedLabels() {
	$(".label").remove();
	$(".product").each(function(index) {
		var number_of_labels = $(this).find(".value").val();

		if (number_of_labels > 0) {
			var label_name = $(this).find(".name").text();
			var label_barcode_string = $(this).attr("product_barcode_string");
			var img = $("<img />");
			img.JsBarcode(label_barcode_string, barcode_options);
			var label = $("<div class='label'><div class='label_name'>" + label_name + "</div></div>");
			label.append(img);
			for (var i = 0; i < number_of_labels; i = i + 1) {
				$("#labels").append(label.clone());				
			}
		}
	});
	
//	$("#wrapper").hide();
	window.print();
}


