// JavaScript Document

var product = new Object();
var is_new_product = false;
var restrictions_array = [];

$(document).ready(function() {
	generateBarCode();
	
	if ($("#barcode_picture").hasClass("hidden")) {
		is_new_product = true;
		$("#ok").show();
//		activateProductEditing();
	} else {
		$("#ingoing").on("click", handleIngoing);
		$("#outgoing").on("click", handleOutgoing);	
		$("#edit").on("click", activateProductEditing);		
		fillProductWithDataBecauseOfTextFields();
	}
	
	
	
	$("#wrapper").on("click", "#ok", confirmProductEditing);
});

function fillProductWithDataBecauseOfTextFields() {
	product.id = $("#product").attr("value");
	product.name = $("#product_name").text();
	product.description = $("#product_description").text();
	product.minimum_limit = $("#product_minimum_limit").text();
	product.lager_quantity = $("#product_lager_quantity").text();
	product.unity = $(".product_unity").get(0).innerHTML;
	product.state = $("#product_state").text();
}

function fillProductWithDataBecauseOfInputFields() {
	product.id = $("#product").attr("value");
	if (isNaN(product.id)) {
		console.log("product.id isNaN");
		return false;
	}
	product.name = $("#product_name").val();
	if (product.name == "") {
		$("#product_name").addClass("problem");
		console.log("product.name is empty");
		return false;
	} else {
		$("#product_name").removeClass("problem");
	}
	product.description = $("#product_description").val();
	if (product.description == "") {
		$("#product_description").addClass("problem");
		console.log("product.description is empty");
		return false;
	} else {
		$("#product_description").removeClass("problem");
	}
	product.minimum_limit = $("#product_minimum_limit").val();
	if (isNaN(product.minimum_limit)) {
		$("#product_minimum_limit").addClass("problem");
		console.log("product.minimum_limit isNaN");
		return false;
	} else {
		$("#product_minimum_limit").removeClass("problem");
	}
	product.unity = new Object();
	product.unity.name = $("#product_unity").val();
	if ($("#product_state").is(":checked")) {
		product.state = "ACTIVE";
	} else {
		product.state = "INACTIVE";
	}
	
	$(".restriction").each(function( index ) {
		if ($(this).is(":checked")) {
			var truck = new Object();
			truck.id = $(this).attr("lkw_id");
			restrictions_array.push(truck);
		}
	});
	product.trucks_to_restrict = restrictions_array;
	
	if ($("#no_restriction").is(":checked")){
		alert("no_restriction...");
		product.restriction = "NO";
		product.trucks_to_restrict = [];
	} else {
		alert("RRRRRestriction...");
		product.restriction = "YES";
	}
	
	//alert("unity = " + product.unity);
}

function activateProductEditing() {
	$("#ok").show();
	$("#edit").hide();

	fillProductWithDataBecauseOfTextFields();
	$("#product").find(".description").remove();
	$("#product").find(".value").remove();
	$("#ingoing").hide();
	$("#outgoing").hide();
	$("#barcode_picture").hide();

	var content = $('<div class="description">Produkt ID:</div>' +
				'<div class="value" id="product_id">' + product.id + '</div>' +
				'<div class="description">Produktname:</div>' +
				'<input type="text" class="value editable text" id="product_name" value="'+ product.name +'"></input>' + 
				'<div class="description">Beschreibung:</div>' +
				'<textarea type="text" class="value editable" id="product_description">' + product.description + '</textarea>' +
				'<div class="description">Mindestmenge im Lager:</div>' +
				'<input type="number" class="value editable" id="product_minimum_limit" value="' + product.minimum_limit + '"></input>' +
				'<select class="value" id="product_unity">' +
				'<option value="Stück">Stück</option>' +
				'<option value="Liter">Liter</option>' +
				'<option value="Paar">Paar</option>' +
				'<option value="Packung">Packung</option>' +
				'<option value="Rolle">Rolle</option>' +
				'</select>' + 
				'<div class="description">Aktives Produkt:<input type="checkbox" id="product_state"></input></div>' +
				'<div class="description" id="product_restrictions_container">Produkt darf nur in folgende LKWs:</div>'
				);
	content.insertAfter("#barcode_picture");
	if (product.state == "ACTIVE") {
		$("#product_state").prop("checked", true);		
	} else {
		$("#product_state").prop("checked", false);		
	}
	var restrictions = $("#restrictions").children();
	restrictions.insertAfter("#product_restrictions_container");
	
	$("#product_unity").val(product.unity).change();
	
	$("input:text").change(checkForCorrectTextValue);
	$("[type='number']").change(checkForCorrectNumberValue);

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
				'<div class="value editable"><span id="product_minimum_limit">' + product.minimum_limit + '</span><span class="product_unity">' + " " + product.unity + '</span></div>' +
				'<div class="description">Lagerbestand:</div>' +
				'<div class="value editable"><span id="product_lager_quantity">' + product.lager_quantity + '</span><span class="product_unity">' + " " + product.unity + '</span></div>' +
				'<div class="hidden value" id="product_state">' + product.state +'</div>'
				);
				
	content.insertAfter("#barcode_picture");
}

function confirmProductEditing() {
	if (!fillProductWithDataBecauseOfInputFields()) {
		alert("Problem");
		return;
	}
	
	if (is_new_product) {
		product.id = "0";
		var product_string = JSON.stringify(product);
		sendProductToServer("Add", product_string);
	} else {
		var product_string = JSON.stringify(product);
		alert("product edit = " + product_string);
		sendProductToServer("Edit", product_string);		
	}
}


function sendProductToServer(servlet, product_string) {
	
	$.ajax({
		type: "POST",
		url: servlet,
		data: { type: "product",
				object: product_string },
		cache: false,
		success: function(data, settings, xhr) {
			//alert("success");
			var id = xhr.getResponseHeader('id');
			location.href = "ProductDetail?id=" + id;
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
	createTransactionPopUp("Eingang...", "Wo kommt es her?", $("#possible_ingoing_locations"));
//	createPopUp("Eingang...", "message");
	createPopUpButtons("Verbuchen", "Abbrechen");
	$("#confirm_pop_up_button").on("click", confirmIngoingTransaction);
}

function handleOutgoing() {
	createTransactionPopUp("Ausgang...", "Wo geht es hin?", $("#possible_outgoing_locations"));
	createPopUpButtons("Verbuchen", "Abbrechen");
	$("#confirm_pop_up_button").on("click", confirmOutgoingTransaction);
}

function confirmIngoingTransaction() {
	$("#confirm_pop_up_button").off("click", confirmIngoingTransaction);
	var quantity = $("#quantity").val();
	var from = $("#location").val();
	var to = $("#current_location").text();
	sendTransaction(quantity, from, to);
}
function confirmOutgoingTransaction() {
	$("#confirm_pop_up_button").off("click", confirmOutgoingTransaction);
	var quantity = $("#quantity").val();
	var from = $("#current_location").text();
	var to = $("#location").val();
	sendTransaction(quantity, from, to);
}

function sendTransaction(quantity, from, to) {
	var product_id = $("#product").attr("value");
	
	$.ajax({
		type: "POST",
		url: "CommitTransaction",
		data: { product_id: product_id,
				from: from,
				to: to, 
				quantity: quantity },
		cache: false,
		success: function(data, settings, xhr) {
			//alert("success, transaction was sent");
			//var content = xhr.getResponseHeader('content');

			location.reload();
			/*
			var old_quantity = parseInt($("#product_lager_quantity").text());
			var quantity_value = parseInt(quantity);			
			if (from == $("#current_location").text()) {
				//lagerabbau
				$("#product_lager_quantity").text(old_quantity - quantity_value);
			} else {
				//lageraufbau
				$("#product_lager_quantity").text("" + (old_quantity + quantity_value));				
			}
			*/
		},
		error: function(data, settings, xhr) {
			alert("error");
//			alert("error message = " + xhr.getResponseHeader('error_message'));
//			$("#pop_up_message").html(xhr.getResponseHeader('error_message'));
		}
	});
}

