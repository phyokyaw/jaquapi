var updateListeners = = new Map();

function addUpdateListener(name, fn) {
	if (!updateListeners.has(name)) {
		updateListeners.set(name, []);
	}
	updateListeners[name].push(fn);
}

function removeUpdateListener(name, fn) {
	if (updateListeners.has(name)) {
		updateListeners[name].pop(fn);
	}
}

$(function() { //DOM Ready
	(function deviceStatusPoll() {		
		$.ajax({
			dataType : "json",
			url : "/",
			type : "GET",
			success : function(data) {
				
//				for(var k in data) {
//					if (data[k].overridden == true) {
//						if (data[k].on == true) {
//							$("#device_" + data[k].id + "_switch_status").text("MAUNAL ON (" + data[k].overridingModeTimeoutFormatted + ")");
//							$("#device_" + data[k].id + "_switch_status").css('background-color',"blue");
//						} else {
//							$("#device_" + data[k].id + "_switch_status").text("MAUNAL OFF (" + data[k].overridingModeTimeoutFormatted + ")");
//							$("#device_" + data[k].id + "_switch_status").css('background-color',"black");
//						}
//					} else {
//						if (data[k].on == true) {
//							$("#device_" + data[k].id + "_switch_status").text("ON (AUTO)");
//							$("#device_" + data[k].id + "_switch_status").css('background-color',"blue");
//						} else {
//							$("#device_" + data[k].id + "_switch_status").text("OFF (AUTO)");
//							$("#device_" + data[k].id + "_switch_status").css('background-color',"black");
//						}
//					}
//					
//				}
			},
			complete : setTimeout(function() {deviceStatusPoll()}, 5000),
			timeout : 1000
		});
	})();
});