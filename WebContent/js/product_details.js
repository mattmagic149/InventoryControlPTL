// JavaScript Document

var product = new Object();

$(document).ready(function() {
	generateBarCode();
	
	$("#ingoing").on("click", handleIngoing);
	$("#outgoing").on("click", handleOutgoing);
	
	$("#edit").on("click", activateProductEditing);
	$("#wrapper").on("click", "#ok", confirmProductEditing);
});

function fillProductWithDataBecauseOfTextFields() {
	product.id = $("#product_id").text();
	product.name = $("#product_name").text();
	product.description = $("#product_description").text();
	product.minimum_limit = $("#product_minimum_limit").text();
	product.lkw_ids = [1,2,3];
}

function fillProductWithDataBecauseOfInputFields() {
	product.name = $("#product_name").val();
	product.description = $("#product_description").val();
	product.minimum_limit = $("#product_minimum_limit").val();
	product.lkw_ids = [1,2,3];
	product.unity = "stk";
}

function activateProductEditing() {
	fillProductWithDataBecauseOfTextFields();
	$("#product").find(".description").remove();
	$("#product").find(".value").remove();
	$("#ingoing").hide();
	$("#outgoing").hide();
	$("#barcode_picture").hide();

	var content = $('<div class="description">Produkt ID:</div>' +
				'<div class="value" id="product_id">' + product.id + '</div>' +
				'<div class="description">Produktname:</div>' +
				'<input type="text" class="value editable" id="product_name" value="'+ product.name +'"></input>' + 
				'<div class="description">Beschreibung:</div>' +
				'<textarea type="text"class="value editable" id="product_description" value="' + product.description + '"></textarea>' +
				'<div class="description">Mindestmenge im Lager:</div>' +
				'<input type="text" class="value editable" id="product_minimum_limit" value="' + product.minimum_limit + '"></input>' +
				'<select class="value" id="#product_unity">' +
				'<option value="Stück">Stück</option>' +
				'<option value="Liter">Liter</option>' +
				'<option value="Paar">Paar</option>' +
				'</select>');
	content.insertAfter("#barcode_picture");
}

function showProductBecauseOfData() {
	fillProductWithDataBecauseOfInputFields();
	$("#product").find(".description").remove();
	$("#product").find(".value").remove();
	$("#ingoing").show();
	$("#outgoing").show();
	$("#barcode_picture").show();

	var content = $('<div class="description">Produkt ID:</div>' +
				'<div class="value" id="product_id">' + product.id + '</div>' +
				'<div class="description">Produktname:</div>' +
				'<div class="value editable" id="product_name">' + product.name + '</div>' +
				'<div class="description">Beschreibung:</div>' +
				'<div class="value editable" id="product_description">' + product.description + '</div>' +
				'<div class="description">Mindestmenge im Lager:</div>' +
				'<div class="value editable"><span id="product_minimum_limit">' + product.minimum_limit + '</span>' + product.unity + '</div>' +
				'<div class="description">Lagerbestand:</div>' +
				'<div class="value editable"><span id="product_lager_quantity">' + product.lager_quantity + '</span>' + product.unity + '</div>');
	
	content.insertAfter("#barcode_picture");
}

function confirmProductEditing() {
	if ($("#product_id").text() == "") {
		product.id = "P-000000";
		alert("product_id was nothing and is now = "+ product.id);
	}

	var product_string = JSON.stringify(product);
	sendProductToServer(product_string);
	showProductBecauseOfData();
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
	if (id != "") {
		$("#barcode_picture").JsBarcode(id, barcode_options);			
	}
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


