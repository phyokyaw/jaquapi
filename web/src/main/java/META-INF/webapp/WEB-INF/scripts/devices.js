$(function() { //DOM Ready
	(function deviceStatusPoll() {		
		$.ajax({
			dataType : "json",
			url : "/devicestatus",
			type : "GET",
			success : function(data) {
				for(var k in data) {
					$("#onoffswitch_" + data[k].id).prop("checked", data[k].on);
				}
			},
			complete : setTimeout(function() {deviceStatusPoll()}, 5000),
			timeout : 1000
		})
	})();
});