$(function() { //DOM Ready
	(function deviceStatusPoll() {		
		$.ajax({
			dataType : "json",
			url : "/",
			type : "GET",
			success : function(data) {
				gauge.onready = function() {
					gauge.setValue(data.value);	
				}
			},
			complete : setTimeout(function() {tempPoll()}, 5000),
			timeout : 1000
		})
	})();
});