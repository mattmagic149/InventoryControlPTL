// JavaScript Document
$(document).ready(function() {
	generateBarCode();
	
	$("#ingoing").on("click", handleIngoing);
	$("#outgoing").on("click", handleOutgoing);
	
	$("#edit").on("click", activateProductEditing);
	$("#wrapper").on("click", "#ok", confirmProductEditing);
});

function activateProductEditing() {
	var elements = $(".editable");
	
}

function confirmProductEditing() {
	var product = new Object();
	product.id = $("#product_id").text();
	product.name = $("#product_name").text();
	product.description = $("#product_description").text();
	product.minimum_limit = $("#product_minimum_limit").text();
	product.lkw_ids = [1,2,3];
	var product_string = JSON.stringify(product);
	sendProductToServer(product_string);
}
function sendProductToServer(product_string) {

	$.ajax({
		type: "POST",
		url: "Add",
		data: { type: "product",
				object: product_string },
		cache: false,
		success: function(data, settings, xhr) {
			alert("success");
			var content = xhr.getResponseHeader('content');
			
		},
		error: function(data, settings, xhr) {
			alert("error");
			
//			$("#pop_up_message").html(xhr.getResponseHeader('error_message'));
		}
	});
}

function generateBarCode() {
	var barcode_options = {
					width:  2,
					height: 100,
					quite: 10,
					format: "CODE128",
					displayValue: true,
					font:"monospace",
					textAlign:"center",
					fontSize: 17,
					backgroundColor:"",
					lineColor:"#000"
				}
	var id = $("#product_id").text();
	$("#barcode_picture").JsBarcode(id, barcode_options);	
}


function handleIngoing() {
	createTransactionPopUp("Eingang...", "Wo kommt es her?", "<option value='new'>Neue Ware...</option><option value='LKW1'>GU PTL 10</option><option value='LKW2'>GU PTL 12</option>");
//	createPopUp("Eingang...", "message");
	createPopUpButtons("Verbuchen", "Abbrechen");
	
}

function handleOutgoing() {
	createTransactionPopUp("Ausgang...", "Wo geht es hin?", "<option value='selection'>Wählen Sie einen LKW aus!</option><option value='LKW1'>GU PTL 10</option><option value='LKW2'>GU PTL 12</option><option value='other'>Andere...</option>");
	createPopUpButtons("Verbuchen", "Abbrechen");
}


