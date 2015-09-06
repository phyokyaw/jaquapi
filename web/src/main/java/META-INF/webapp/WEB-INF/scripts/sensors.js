//$(function() { //DOM Ready
//	(function deviceStatusPoll() {		
//		$.ajax({
//			dataType : "json",
//			url : "/sensors_status",
//			type : "GET",
//			success : function(data) {
//				for(var k in data) {
//					if (data[k].onError == true) {
//						$("#sensor_" + data[k].id + "_switch_status").text(data[k].onErrorMessage);
//						$("#sensor_" + data[k].id + "_switch_status").css('background-color',"red");
//						if ($("#sensor_detail_" + data[k].id + "_switch_status").length) {
//							$("#sensor_detail_" + data[k].id + "_switch_status").text(data[k].onErrorMessage);
//							$("#sensor_detail_" + data[k].id + "_switch_status").css('color',"red");
//						}
//					} else {
//						$("#sensor_" + data[k].id + "_switch_status").text("OK");
//						$("#sensor_" + data[k].id + "_switch_status").css('background-color',"green");
//						if ($("#sensor_detail_" + data[k].id + "_switch_status").length) {
//							$("#sensor_detail_" + data[k].id + "_switch_status").text("OK");
//							$("#sensor_detail_" + data[k].id + "_switch_status").css('color',"green");
//						}
//					}
//				}
//			},
//			complete : setTimeout(function() {deviceStatusPoll()}, 5000),
//			timeout : 1000
//		})
//	})();
//});