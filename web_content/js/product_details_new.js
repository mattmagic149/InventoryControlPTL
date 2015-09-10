// JavaScript Document
$(document).ready(function() {
  
  $("#add_button").on("click", addNewAnswer);
	$("#possible_answers").on("mouseenter", ".answer_class", showIcons).on("mouseleave", ".answer_class", hideIcons);
	$("#possible_answers").on("click", ".close_button", removeAnswer);
	$("#possible_answers").on("click", ".upload_button", uploadImage);
	
	$("#possible_answers").on("click", "input[type=checkbox]", toggleInputField);
	$("#wrapper").on("click", "#ok", confirmQuestion);
	
	
	$('.editable').inlineEdit({
    //buttons: '<a href="#" class="save"><i class="icon-ok"></i></a> <a href="#" class="cancel"><i class="icon-remove"></i></a>',
		buttons: '',
		saveOnBlur: true,
   });		
});

function confirmQuestion() {
    alert("Frage wird auf den Server hochgeladen");	
}

//----------------------------------------------------------------------------------------------
// This function adds a new answer entry. 
//
function addNewAnswer() {
	var number_of_items = $('.answer_class').length;
	
	$("#add_button").before("<div class='color_discreet answer_class'>" + 
	"<input type='checkbox' value='' /><p class='editable'>" + ++number_of_items + ". Antwort Möglichkeit</p>" + 
	"<section class='upload_button'></section><section class='close_button'></section></div>");
	//Damit beim Hinzufügen einer neuen Antwortmöglichkeit immer ganz nach unten gescrollt wird
	var objDiv = document.getElementById("possible_answers");
	objDiv.scrollTop = objDiv.scrollHeight;
}

//----------------------------------------------------------------------------------------------
// This function removes an answer entry. 
//
function removeAnswer() {
	$(this).parent(".answer_class").remove();
}

//----------------------------------------------------------------------------------------------
// This function shows the close and upload button 
//
function showIcons() {
//	$(this).children(".close_button").css("display","block");
	$(this).children(".upload_button").show();
	$(this).children(".close_button").show();
}

//----------------------------------------------------------------------------------------------
// This function hides the close and upload button 
//
function hideIcons() {
//	$(this).children(".close_button").css("display","none");
	$(this).children(".upload_button").hide();
	$(this).children(".close_button").hide();
}

//----------------------------------------------------------------------------------------------
// This function toggles the class for the inputfield and changes the color of the icons
//
function toggleInputField() {
	$(this).parent().toggleClass("color_discreet").toggleClass("given_answer");
	$(this).siblings(".close_button").toggleClass("close_button_black");
	$(this).siblings(".upload_button").toggleClass("upload_button_black");
}

//----------------------------------------------------------------------------------------------
// This function handles the image upload
//
function uploadImage() {
    alert("Imageupload-Function");
}