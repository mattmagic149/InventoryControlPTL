// JavaScript Document
var service = new Object();

$(document).ready(function() {
	$("#item_list").on("click", ".details", handleClickOnDetailsButton);
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
	
	var service_string = JSON.stringify(service);
	console.log("service = " + service_string);
	
	createServicePopUp("Service ändern", service,  $("#all_possible_repair_shops"));
	createPopUpButtons("Eintragen", "Abbrechen");
	$("#confirm_pop_up_button").on("click", confirmServiceEditing);
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
}

function fillServiceBecauseOfInputFields() {
	service.date = $("#pop_up_date").val();
	service.repair_shop = new Object();
	service.repair_shop.id = $("#pop_up_repair_shop").val();
	if (service.repair_shop.id == 0) {
		service.repair_shop.name = $("#pop_up_repair_shop_name").val();
		service.repair_shop.location = $("#pop_up_repair_shop_location").val();
	}
	service.description = $("#pop_up_description").text();
	service.mileage = $("#pop_up_mileage").val();
	var service_string = JSON.stringify(service); 
	console.log("service from input fields = " + service_string);
}

function confirmServiceEditing() {
	fillServiceBecauseOfInputFields();

	var service_string = JSON.stringify(service);
	sendServiceToServer("Edit", service_string);
}

function confirmNewService() {
	fillServiceBecauseOfInputFields();
	
	var service_string = JSON.stringify(service);
	sendServiceToServer("Add", service_string);	
}


function sendServiceToServer(servlet, service_string) {
	
	$.ajax({
		type: "POST",
		url: servlet,
		data: { type: "truck_service",
				object: service_string },
		cache: false,
		success: function(data, settings, xhr) {
			alert("success");
			location.href = "LkwServices?id=" + $("#truck_id").text();
		},
		error: function(data, settings, xhr) {
			alert("error");
//			$("#pop_up_message").html(xhr.getResponseHeader('error_message'));
		}
	});
}

