var phHistoryChart;
var ph_interval = 'HOUR';
$(function() { //DOM Ready
	function phHistoryPoll(recall) {		
		$.ajax({
			dataType : "json",
			url : "/ph_history?interval=" + ph_interval,
			type : "GET",
			success : function(phData) {	
				var pdata = {
					labels : phData.labels,
					datasets : [
					{
						fillColor : "rgba(151,187,205,0.5)",
						strokeColor : "rgba(151,187,205,1)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						data : phData.values,
					}]
				};
				options = {
					animation : false
				}
				if (phHistoryChart != null) {
					phHistoryChart.destroy();
				}
				var ctx = $("#phHistory").get(0).getContext("2d");
				phHistoryChart = new Chart(ctx).Line(pdata, options);
			},
			complete :  function() {
				if (recall) {
					setTimeout(function() {phHistoryPoll(true)}, 10000)
				}
			},
			timeout : 1000
		});
	}
	phHistoryPoll(true);

	var gauge = new Gauge({
		renderTo    : 'phGuage',
		width       : 150,
		height      : 150,
		glow        : true,
		units       : "",
		title       : "Ph",
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
	gauge.draw();
	
	(function phPoll() {		
		$.ajax({
			dataType : "json",
			url : "/ph",
			type : "GET",
			success : function(data) {
				gauge.onready = function() {
					gauge.setValue(data.value);	
				}
			},
			complete : setTimeout(function() {phPoll()}, 5000),
			timeout : 1000
		})
	})();
	
	$("[name=phHistorySelection]").change(function() {
		ph_interval = $(this).val();
		phHistoryPoll(false);
	});
});
