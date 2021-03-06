// JavaScript Document

$(document).ready(function() {
	getValidIdsFromServer();
	startReadingBarcode();
});

var has_valid_id_list = false;
var quagga_is_ready = false;
var valid_ids = [];
var counter_invalid_codes = 0;

function startReadingBarcode() {
	var App = {
        init : function() {
            var self = this;
            Quagga.init(this.state, function(err) {
                if (err) {
                	var new_error_obj = $('<h2 id="error_message">Leider konnte keine Kamera gefunden werden... :(</h2>');
                	new_error_obj.insertAfter("h1");
                    return self.handleError(err);
                }
                App.attachListeners();
				quagga_is_ready = true;
				startQuaggaIfListAndQuaggaIsReady();
            });
        },
        handleError: function(err) {
            console.log(err);
        },
        attachListeners: function() {
            var self = this;
        },
        state: {
            locate: true
        }
    };
    
	App.init();
    
    Quagga.onProcessed(function(result) {
        var drawingCtx = Quagga.canvas.ctx.overlay,
            drawingCanvas = Quagga.canvas.dom.overlay;

        if (result) {
            if (result.boxes) {
                drawingCtx.clearRect(0, 0, parseInt(drawingCanvas.getAttribute("width")), parseInt(drawingCanvas.getAttribute("height")));
                result.boxes.filter(function (box) {
                    return box !== result.box;
                }).forEach(function (box) {
                    Quagga.ImageDebug.drawPath(box, {x: 0, y: 1}, drawingCtx, {color: "green", lineWidth: 2});
                });
            }

            if (result.box) {
                Quagga.ImageDebug.drawPath(result.box, {x: 0, y: 1}, drawingCtx, {color: "#00F", lineWidth: 2});
            }

            if (result.codeResult && result.codeResult.code) {
                Quagga.ImageDebug.drawPath(result.line, {x: 'x', y: 'y'}, drawingCtx, {color: 'red', lineWidth: 3});
            }
        }
    });

    Quagga.onDetected(function(result) {
        var code = result.codeResult.code;
		if (isValidCode(code)) {
			var servlet = getCorrectServlet(code);
			var id = getId(code);
			var link = servlet + "?id=" + id;
			console.log("Scanned valid code = " + code);
			location.href = link;
		}
    });
}

function isValidCode(code) {	
	var index = valid_ids.indexOf(code);
	if (index == -1) {
		counter_invalid_codes = counter_invalid_codes + 1;
		console.log("Scanned not valid code = " + code);
		if (counter_invalid_codes > 3) {
			alert("Es wurde leider kein gültiger Barcode erkannt. (" + code + ")");
			counter_invalid_codes = 0;
		}
		return false;
	} else {
		return true;
	}
}

function startQuaggaIfListAndQuaggaIsReady() {
	if (has_valid_id_list) {
		if (quagga_is_ready) {
			Quagga.start();
		}
	}
}

function getCorrectServlet(code) {
	var first_two_char = code.substring(0, 1);
	if (first_two_char == "P") {
		return "ProductDetail";
	} else if (first_two_char == "L") {
		return "LkwDetail";
	} else if (first_two_char == "I") {
		return "LagerDetail";
	} else {
		return "Unknown";
	}
}

function getId(code) {
	return code.substring(1, code.length - 1);
}



function getValidIdsFromServer() {	
	$.ajax({
		type: "POST",
		url: "GetValidBarCodes",
		data: { products: "true",
				trucks: "true",
				inventories: "true" },
		cache: false,
		success: function(data, settings, xhr) {
			var list = xhr.getResponseHeader('list');
			valid_ids = jQuery.parseJSON(list);
			has_valid_id_list = true;
			startQuaggaIfListAndQuaggaIsReady();
		},
		error: function(data, settings, xhr) {
			alert("Error beim Abrufen der Barcodes vom Server. Es wird nochmal versucht...");
			location.reload();
//			$("#pop_up_message").html(xhr.getResponseHeader('error_message'));
		}
	});
}
