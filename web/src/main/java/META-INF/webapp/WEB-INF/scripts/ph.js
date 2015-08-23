$(function() { //DOM Ready
	var phGauge = new Gauge({
		renderTo    : 'phGuage',
		width       : 135,
		height      : 135,
		glow        : true,
		units       : "",
		title       : "pH",
		minValue    : 6,
		maxValue    : 10,
		majorTicks  : ['6','7','8','9','10'],
		minorTicks  : 0.25,
		strokeTicks : false,
		highlights  : [
			{ from : 6, to : 7.5, color : '#ff33aa' },
			{ from : 7.5, to : 8.5, color : '#00cc00' },
			{ from : 8.5, to : 10, color : '#ff0000' }
		],
		colors      : {
			needle     : { start : '#f00', end : '#00f' }
		}
	});
	phGauge.onready = function() {
		(function phPoll() {		
			$.ajax({
				dataType : "json",
				url : "/ph",
				type : "GET",
				success : function(data) {
					phGauge.setValue(data.value);
				},
				complete : setTimeout(function() {phPoll()}, 5000),
				timeout : 1000
			})
		})();
	}
	phGauge.draw();
});
