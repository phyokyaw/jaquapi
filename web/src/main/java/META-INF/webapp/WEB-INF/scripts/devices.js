$(function() { //DOM Ready
	(function deviceStatusPoll() {		
		$.ajax({
			dataType : "json",
			url : "/device_status",
			type : "GET",
			success : function(data) {
				for(var k in data) {
					if (data[k].on == true) {
						$("#device_" + data[k].id + "_switch_status").text("ON");
						$("#device_" + data[k].id + "_switch_status").css('background-color',"blue");
					} else {
						$("#device_" + data[k].id + "_switch_status").text("OFF");
						$("#device_" + data[k].id + "_switch_status").css('background-color',"black");
					}
				}
			},
			complete : setTimeout(function() {deviceStatusPoll()}, 5000),
			timeout : 1000
		})
	})();
});