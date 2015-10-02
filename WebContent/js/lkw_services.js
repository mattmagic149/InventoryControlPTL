// JavaScript Document
var service = new Object();

$(document).ready(function() {
	$("#item_list").on("click", ".details", handleClickOnDetailsButton);
	$("#item_list").on("click", ".delete", handleClickOnDeleteButton);
	$("#add_button").on("click", handleClickOnNewServiceButton);
	
	service.truck = new Object();
	service.truck.id = $("#truck_id").text();
});

function handleClickOnDetailsButton(e) {
	var item = $(e.target).parent();
	service.id = item.attr("id");
	service.date = item.find(".date").first().text();
	service.repair_shop = new Object();
	service.repair_shop.id = item.find(".repair_shop").first().attr("repair_shop_id");
	service.description = item.find(".description").first().text();
	service.mileage = item.find(".mileage").first().text();
	
	console.log("handleClickOnDetailsButton: Extracted Service = '" + JSON.stringify(service) + "'");
	
	createServicePopUp("Service ändern", service,  $("#all_possible_repair_shops"));
	createPopUpButtons("Eintragen", "Abbrechen");
	
	$("#confirm_pop_up_button").on("click", confirmServiceEditing);
	
	createInputFieldsChecker();
}
function handleClickOnDeleteButton(e) {
	var item = $(e.target).parent();
	service.id = item.attr("id");
	service.date = item.find(".date").first().text();
	service.repair_shop = new Object();
	service.repair_shop.id = item.find(".repair_shop").first().attr("repair_shop_id");
	service.repair_shop.name = item.find(".repair_shop").first().text();
	service.description = item.find(".description").first().text();
	service.mileage = item.find(".mileage").first().text();
	
	console.log("handleClickOnDeleteButton: Extracted Service = '" + JSON.stringify(service) + "'");
	
	createServiceDeletePopUp("Service Löschen", service,  $("#all_possible_repair_shops"));

	createDeletePopUpButtons("Löschen", "Abbrechen");
	$("#delete_pop_up_button").on("click", confirmServiceDeleting);
	
	createInputFieldsChecker();
}

function handleClickOnNewServiceButton(e){
	service.id = "0";
	service.date = "";
	service.repair_shop = new Object();
	service.repair_shop.id = -1;
	service.description = "";
	service.mileage = "";
	
	createServicePopUp("Neues Service hinzufügen", service, $("#all_possible_repair_shops"));
	createPopUpButtons("Eintragen", "Abbrechen");
	$("#confirm_pop_up_button").on("click", confirmNewService);

	createInputFieldsChecker();
}

function fillServiceBecauseOfInputFields() {
	service.date = $("#pop_up_date").val();
	service.repair_shop = new Object();
	service.repair_shop.id = $("#pop_up_repair_shop").val();
	if (service.repair_shop.id == 0) {
		service.repair_shop.name = $("#pop_up_repair_shop_name").val();
		service.repair_shop.location = $("#pop_up_repair_shop_location").val();
	}
	service.description = $("#pop_up_description").val();
	service.mileage = $("#pop_up_mileage").val();

	console.log("fillServiceBecauseOfInputFields: Service = '" + JSON.stringify(service) + "'");
}

function confirmServiceEditing() {
	if (!(checkAllInputFields())) {
		console.log("confirmServiceEditing: checkAllInputFields dedected a problem");
		return;
	}
	
	fillServiceBecauseOfInputFields();

	var service_string = JSON.stringify(service);
	sendServiceToServer("Edit", service_string);
}
function confirmServiceDeleting() {
	var service_string = JSON.stringify(service);
	sendServiceToServer("Delete", service_string);
}

function confirmNewService() {
	if (!(checkAllInputFields())) {
		console.log("confirmNewService: checkAllInputFields dedected a problem");
		return;
	}

	fillServiceBecauseOfInputFields();
	
	var service_string = JSON.stringify(service);
	sendServiceToServer("Add", service_string);	
}


function sendServiceToServer(servlet, service_string) {
	console.log("sendServiceToServer: Servlet = '" + servlet + "', Service = '" + service_string + "'");

	$.ajax({
		type: "POST",
		url: servlet,
		data: { type: "truck_service",
				object: service_string },
		cache: false,
		success: function(data, settings, xhr) {
			closeAddingInput();
			location.reload();
		},
		error: function(data, settings, xhr) {
			var message = "Fehler beim Senden an den Server";
			createNotification("Error", message, "failure");
		}
	});
}
