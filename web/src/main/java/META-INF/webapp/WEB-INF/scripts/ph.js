//$(function() { //DOM Ready
//	var phGauge = new Gauge({
//		renderTo    : 'phGuage',
//		width       : 135,
//		height      : 135,
//		glow        : true,
//		units       : "",
//		title       : "pH",
//		minValue    : 7,
//		maxValue    : 9,
//		majorTicks  : ['7','7.5','8','8.5','9'],
//		minorTicks  : 0.25,
//		strokeTicks : false,
//		highlights  : [
//			{ from : 7, to : 7.7, color : '#ff33aa' },
//			{ from : 7.7, to : 8.4, color : '#00cc00' },
//			{ from : 8.4, to : 9, color : '#ff0000' }
//		],
//		colors      : {
//			needle     : { start : '#f00', end : '#00f' }
//		}
//	});
//	phGauge.onready = function() {
//		(function phPoll() {		
//			$.ajax({
//				dataType : "json",
//				url : "/ph",
//				type : "GET",
//				success : function(data) {
//					phGauge.setValue(data.value);
//				},
//				complete : setTimeout(function() {phPoll()}, 5000),
//				timeout : 1000
//			})
//		})();
//	}
//	phGauge.draw();
//});
