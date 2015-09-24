//Javascript Document

function checkForCorrectTextValue(e) {
	if ($(e.target).val() == "") {
		$(e.target).addClass("problem");
	} else {
		$(e.target).removeClass("problem");
	}
}

function checkForCorrectNumberValue(e) {
	if (isNaN($(e.target).val())) {
		$(e.target).addClass("problem");
	} else {
		$(e.target).removeClass("problem");
	}
}
