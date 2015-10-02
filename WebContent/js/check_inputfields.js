//Javascript Document

function createInputFieldsChecker () {
	$("input:text").blur(checkForCorrectTextValue);
	$("textarea").blur(checkForCorrectTextValue);
	$('input[type="number"]').blur(checkForCorrectNumberValue);
}

function checkAllInputFields() {
	console.log("checkAllInputFields start");
	var all_inputtext = $("input:text");
	for (var i = 0; i < all_inputtext.length; i++) {
		console.log("i = " + i);
		if ($(all_inputtext[i]).val() == ""  && !($(all_inputtext[i]).hasClass("hidden"))) {
			if (!($(all_inputtext[i]).is("#search"))) {
				$(all_inputtext[i]).addClass("problem");
				console.log("problem exists in all_inputtext");
				return false;				
			}
		}
	}
	var all_inputnumber = $('input[type="number"]');
	for (var i = 0; i < all_inputnumber.length; i++) {
		console.log("i = " + i);
		if ($(all_inputnumber[i]).val() == "" && !($(all_inputnumber[i]).hasClass("hidden"))) {
			$(all_inputnumber[i]).addClass("problem");
			console.log("problem exists in all_inputnumber");
			return false;
		}
	}
	var all_textareas = $("textarea");
	for (var i = 0; i < all_textareas.length; i++) {
		console.log("i = " + i);
		if ($(all_textareas[i]).val() == "" && !($(all_textareas[i]).hasClass("hidden"))) {
			$(all_textareas[i]).addClass("problem");
			console.log("problem exists");
			return false;
		}
	}
	console.log("checkAllInputFields finished");
	return true;
}
function checkForCorrectTextValue(e) {
//	console.log("checkForCorrectTextValue value = " + e.target.value);
	if ($(e.target).val() == "") {
		$(e.target).addClass("problem");
		return false;
	} else {
		$(e.target).removeClass("problem");
		return true;
	}
}

function checkForCorrectNumberValue(e) {	
	if (!($.isNumeric(e.target.value))) {
		$(e.target).addClass("problem");
		return false;
	} else {
		$(e.target).removeClass("problem");
		return true;
	}
}
