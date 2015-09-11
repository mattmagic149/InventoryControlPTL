// JavaScript Document
$(document).ready(function() {
	getValidIdsFromServer();
//	startReadingBarcode();
	alert(getId("000001"));
	alert(getType("000001"));
	alert(getId("1204832"));
	alert(getType("104042"));
	alert(getType("32524"));
});

var has_valid_id_list = false;
var quagga_is_ready = false;
var valid_ids;

function startReadingBarcode() {
	var App = {
        init : function() {
            var self = this;
            Quagga.init(this.state, function(err) {
                if (err) {
					$("#error_message").text("Leider konnte keine Kamera gefunden werden... :(");
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
			alert("referring to " + link);
			location.href = link;
		}		
    });
}

function isValidCode(code) {
	var index = valid_ids.indexOf(code);
	if (index == -1) {
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
	if (first_two_char == "P-") {
		return "Product";
	} else if (first_two_char == "L-") {
		return "LKW";
	} else if (first_two_char == "I-") {
		return "Lager";
	} else {
		return "Unknown";
	}
}

function getId(code) {
	return code.substring(2, code.length);
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
			alert("success");
			valid_ids = xhr.getResponseHeader('list');
			alert("list = " + list);
			//var asdf = ["P-000002", "asdf"];
			alert("valid_ids = " + valid_ids);
//			alert("is valid = " + isValidCode("P-000002"));
//			alert("is valid = " + isValidCode("P-00002"));
			
		},
		error: function(data, settings, xhr) {
			alert("error");
//			$("#pop_up_message").html(xhr.getResponseHeader('error_message'));
		}
	});
}
