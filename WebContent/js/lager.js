// JavaScript Document

$(document).ready(function() {
	$("#edit").on("click", activateListEditing);
	
	$("#wrapper").on("click", "#ok", LagerEditingConfirmed);
	$("#wrapper").on("click", "#cancel", editingCanceled);
	$("#list").on("click", ".close_button", removeListEntry);
	$("#wrapper").on("click", "#add_button", addNewInventory);
});

function LagerEditingConfirmed() {
  deactivateListEditing();
}

function addNewInventory() {
	$(this).before("<div class='list_entry color'><a href='#'>" +
                    "<p class='editable'>Neues Lager</p></a>" + 
                    "<section class='close_button'></section></div>");
		
	//Damit beim Hinzufügen einer neuen Antwortmöglichkeit immer ganz nach unten gescrollt wird
	var objDiv = document.getElementById("list");
	objDiv.scrollTop = objDiv.scrollHeight;
}