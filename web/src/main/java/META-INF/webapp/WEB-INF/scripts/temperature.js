
$(function() { //DOM Ready
	var temp_gauge = new Gauge({
		renderTo    : 'tempGuage',
		width       : 135,
		height      : 135,
		glow        : true,
		units       : "C&deg;",
		title       : "Temp",
		minValue    : 21,
		maxValue    : 30,
		majorTicks  : ['21','22','23','24','25','26','27','28','29', '30'],
		minorTicks  : 0.5,
		strokeTicks : false,
		highlights  : [
			{ from : 21, to : 24, color : '#ff33aa' },
			{ from : 24, to : 27, color : '#00cc00' },
			{ from : 27, to : 30, color : '#ff0000' }
		],
		colors      : {
			needle     : { start : '#f00', end : '#00f' }
		},
		animation : {
			delay : 25,
			duration: 500,
			fn : 'bounce'
		}
	});
	temp_gauge.onready = function() {
		(function tempPoll() {		
			$.ajax({
				dataType : "json",
				url : "/temperature",
				type : "GET",
				success : function(data) {
					temp_gauge.setValue(data.value);
				},
				complete : setTimeout(function() {tempPoll()}, 5000),
				timeout : 1000
			});
		})();
	};
	
	temp_gauge.draw();
});
