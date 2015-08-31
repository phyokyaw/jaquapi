$(function() { //DOM Ready
	(function deviceStatusPoll() {		
		$.ajax({
			dataType : "json",
			url : "/device_status",
			type : "GET",
			success : function(data) {
				for(var k in data) {
					if (data[k].overridden == true) {
						if (data[k].on == true) {
							$("#device_" + data[k].id + "_switch_status").text("ON (" + data[k].overridingModeTimeout + ")");
							$("#device_" + data[k].id + "_switch_status").css('background-color',"blue");
						} else {
							$("#device_" + data[k].id + "_switch_status").text("OFF (" + data[k].overridingModeTimeout + ")");
							$("#device_" + data[k].id + "_switch_status").css('background-color',"black");
						}
					} else {
						if (data[k].on == true) {
							$("#device_" + data[k].id + "_switch_status").text("ON (AUTO)");
							$("#device_" + data[k].id + "_switch_status").css('background-color',"blue");
						} else {
							$("#device_" + data[k].id + "_switch_status").text("OFF (AUTO)");
							$("#device_" + data[k].id + "_switch_status").css('background-color',"black");
						}
					}
					
				}
			},
			complete : setTimeout(function() {deviceStatusPoll()}, 5000),
			timeout : 1000
		})
	})();
});