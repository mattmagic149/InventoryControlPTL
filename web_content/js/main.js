// JavaScript Document
$(document).ready(function() {
	
	formatFields();
	
});

//----------------------------------------------------------------------------------------------
// This function checks or unchecks the checkbox and colors the background of the divs. 
//
function formatFields() {
	var number_of_items = $('#admin_links div').length;
	var nav_elements = $('#admin_links div');
	
	if(number_of_items % 2) {
		nav_elements.first().css({"width":"96%", "border-top-left-radius":"10px",
			"border-top-right-radius":"10px","font-size":"1.5em"});
		
	} else {
		nav_elements.first().css({"border-top-left-radius":"10px"});
		nav_elements.eq(1).css({"border-top-right-radius":"10px"});
	}
	
	nav_elements.eq(number_of_items - 2).css({"border-bottom-left-radius":"10px"});
	nav_elements.last().css({"border-bottom-right-radius":"10px"});
}
