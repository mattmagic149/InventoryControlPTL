// JavaScript Document
var extension_obj = null;

$(document).ready(function() {
	$("#item_list").on("click", ".details", handleClickOnDetailsButton);
	$("#add_button").on("click", handleClickOnNewProductButton);
});

function handleClickOnDetailsButton(e) {
	var id = $(e.target).parent().attr("id");
	location.href = 'ProductDetail?id=' + id;
}

function handleClickOnNewProductButton(e) {
	location.href = 'ProductNew.html';
}
/*
function sendPermissionChangeToServer(obj) {

	var new_permission = obj.val();

	var user_id = obj.prop("name");
	
	$.ajax({
		type: "POST",
		url: "UserAdministration",
		data: { new_permission: new_permission, user_id: user_id },
		cache: false,
		success: function(response){
			//$("#person_list").append(response);
		}
	});
	
}
*/