//Javascript Document

function activateEditing() {
  $("#edit").off("click");
  $("#edit").toggleClass("color").toggleClass("color_discreet");  
  

  $('.editable').inlineEdit({
		buttons: '',
		saveOnBlur: true,
	});
}

//----------------------------------------------------------------------------------------------
// Shows all editing Buttons and lets the Creator edit all Content
//
function activateListEditing() {
	activateEditing();
	createEditingElements("#list");
	$("#list").on("click", "a", function(e){e.preventDefault();}); //disable the links
	$(".list_entry").find("p").addClass("editable");
	$("#list").on("mouseenter", ".list_entry", showCloseButton).on("mouseleave", ".list_entry", hideCloseButton);	
}

//----------------------------------------------------------------------------------------------
// Creates all the editing buttons
//
function createEditingElements(content) {
  $(content).append("<section id='add_button' class='color'></section>");
	$(content).after("<section id='ok' class='color'>OK</section> " +
                     "<section id='cancel' class='color_discreet'>Abbrechen</section>");
}
//----------------------------------------------------------------------------------------------
// Deletes all the editing buttons
//
function deactivateEditing() {
	$("#add_button").remove();
	$("#ok").remove();
	$("#cancel").remove();
  $("#edit").toggleClass("color").toggleClass("color_discreet"); 
}



function deactivateListEditing() {
	deactivateEditing();
  $("#edit").on("click", activateListEditing);

  $("#list").children("a").off("click");
	$("#list").off("mouseenter").off("mouseleave");
  $("#list").off("click", "a"); //enables clickable links
  $("#list").find("p").removeClass("editable");
}



//----------------------------------------------------------------------------------------------
// Cancels the editing process (reloads the page)
//
function editingCanceled() {
	location.reload();
}

//----------------------------------------------------------------------------------------------
// This function shows the close and upload button 
//
function showIcons() {
	$(this).find(".upload_button").show();
	$(this).find(".close_button").show();
}

//----------------------------------------------------------------------------------------------
// This function hides the close and upload button 
//
function hideIcons() {
	$(this).find(".upload_button").hide();
	$(this).find(".close_button").hide();
}

//----------------------------------------------------------------------------------------------
// This function shows the close and upload button 
//
function showCloseButton() {
	$(this).find(".close_button").show();
}

//----------------------------------------------------------------------------------------------
// This function hides the close and upload button 
//
function hideCloseButton() {
	$(this).find(".close_button").hide();
}

//----------------------------------------------------------------------------------------------
// This function removes an answer entry. 
//
function removeListEntry() {
	$(this).closest(".list_entry").remove();
}

function removeAnswer() {
	$(this).closest(".answer_class").remove();
}

