// JavaScript Document
$(document).ready(function() {
//	toggleScanningAndDetails();
	startReadingBarcode();
});

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
                Quagga.start();
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
		$("#product_id").text(code);
//		toggleScanningAndDetails();
		sendRequestToServer(code);
    });
}

function sendRequestToServer(code) {
	$.ajax({
		type: "POST",
		url: "Display",
		data: { type: "Product",
				id: code },
		cache: false,
		success: function(data, settings, xhr) {
			alert("success");
			var content = xhr.getResponseHeader('content');
			printProductDetails(content);			
		},
		error: function(data, settings, xhr) {
			alert("error");
//			$("#pop_up_message").html(xhr.getResponseHeader('error_message'));
		}
	});
}

function printProductDetails() {
	$("#product_details_container").removeClass("toggle");
	$("#scanning_container").addClass("toggle");
	var barcode_options = {
					width:  2,
					height: 100,
					quite: 10,
					format: "CODE128",
					displayValue: true,
					font:"monospace",
					textAlign:"center",
					fontSize: 17,
					backgroundColor:"",
					lineColor:"#000"
				}
	var id = $("#product_id").text();
	$("#barcode_picture").JsBarcode(id, barcode_options);
	
	$("#ingoing").on("click", handleIngoing);
	$("#outgoing").on("click", handleOutgoing);
	$("#edit").on("click", handleClickOnEditButton);
	Quagga.stop();
}


function toggleScanningAndDetails() {
	$("#product_details_container").toggleClass("toggle");
	$("#scanning_container").toggleClass("toggle");
}

function handleIngoing() {
	createTransactionPopUp("Eingang...", "Wo kommt es her?", "<option value='new'>Neue Ware...</option><option value='LKW1'>GU PTL 10</option><option value='LKW2'>GU PTL 12</option>");
//	createPopUp("Eingang...", "message");
	createPopUpButtons("Verbuchen", "Abbrechen");
}

function handleOutgoing() {
	createTransactionPopUp("Ausgang...", "Wo geht es hin?", "<option value='selection'>Wählen Sie einen LKW aus!</option><option value='LKW1'>GU PTL 10</option><option value='LKW2'>GU PTL 12</option><option value='other'>Andere...</option>");
	createPopUpButtons("Verbuchen", "Abbrechen");
}

function handleClickOnEditButton(e) {
	var id = $("#product_id").text();
	location.href = 'ProductNew.html?id=' + id;
}

