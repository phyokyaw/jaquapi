<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Gtest</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/jquery.gridster.min.css' />">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/style.css' />" />
<script type="text/javascript" src="<c:url value='/s/gauge.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/Chart.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery-1.11.0.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery.gridster.min.js' />"></script>
<script>
	$(function() { //DOM Ready
		$(".gridster > ul").gridster({
			widget_margins : [ 5, 5 ],
			widget_base_dimensions : [ 200, 200 ]
		}).data('gridster').disable();
		//Get context with jQuery - using jQuery's .get() method.
		var ctx = $("#tempHistory").get(0).getContext("2d");
		//This will get the first returned node in the jQuery collection.
		var myNewChart = new Chart(ctx);
		var data = {
	labels : ["January","February","March","April","May","June","July"],
	datasets : [

		{
			fillColor : "rgba(151,187,205,0.5)",
			strokeColor : "rgba(151,187,205,1)",
			pointColor : "rgba(151,187,205,1)",
			pointStrokeColor : "#fff",
			data : [28,48,40,19,96,27,100]
		}
	]
}
myNewChart.Line(data);
 ctx = $("#phHistory").get(0).getContext("2d");
		//This will get the first returned node in the jQuery collection.
		myNewChart = new Chart(ctx);
		data = {
	labels : ["January","February","March","April","May","June","July"],
	datasets : [
		{
				fillColor : "rgba(151,187,205,0.5)",
			strokeColor : "rgba(151,187,205,1)",
			pointColor : "rgba(151,187,205,1)",
			pointStrokeColor : "#fff",
			data : [7.5,7.2,7.8,7.6,8.2,8.7,8.9]
		}
	]
}
myNewChart.Line(data);
	});
	(function poll() {
		setTimeout(function() {
			$.ajax({
				dataType : "json",
				url : "/temperature",
				type : "GET",
				success : function(data) {
					Gauge.Collection.get('tempGuage').setValue(data.value);
				},
				dataType : "json",
				complete : poll,
				timeout : 2000
			})
		}, 2000);
	})();
	(function poll1() {
		setTimeout(function() {
			$.ajax({
				dataType : "json",
				url : "/ph",
				type : "GET",
				success : function(data) {
					Gauge.Collection.get('phGuage').setValue(data.value);
				},
				dataType : "json",
				complete : poll1,
				timeout : 2000
			})
		}, 2000);
	})();
</script>
</head>

<body>
	<H2 align="center">Aqua control</H2>
	<div class="gridster">
    <ul>
        <li data-row="1" data-col="1" data-sizex="1" data-sizey="1">
        <H3 class="controls">Temperature</H3>
				<canvas id="tempGuage" width="150" height="150"
					data-type="canv-gauge" data-title="Temp" data-min-value="15"
					data-max-value="30"
					data-major-ticks="15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30"
					data-minor-ticks=".5" data-stroke-ticks="true" data-units="C&deg;"
					data-value-format="3.2" data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="15 20 #ff0000, 20 23 #ffff00, 23 28 #00cc00, 28 30 #eaa"></canvas>
        </li>
        <li data-row="2" data-col="1" data-sizex="1" data-sizey="1">
        <H3 class="controls">Ph Meter</H3>
				<canvas id="phGuage"  width="150" height="150" data-type="canv-gauge"
					data-title="Ph" data-min-value="4" data-max-value="12"
					data-major-ticks="4 5 6 7 8 9 10 11 12" data-minor-ticks=".5"
					data-stroke-ticks="true" data-units="Level" data-value-format="3.2"
					data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="4 5 #ff0000, 5 7.7 #ffff00, 7.7 8.8 #00cc00, 8.8 12 #eaa"></canvas>
        </li>
        <li data-row="3" data-col="1" data-sizex="1" data-sizey="1">
        <H3 class="controls">ORP Meter</H3>
				<canvas id="orpGuage"  width="150" height="150"
					data-type="canv-gauge" data-title="ORP" data-min-value="0"
					data-max-value="1000"
					data-major-ticks="0 100 200 300 400 500 600 700 800 900 1000"
					data-minor-ticks="50" data-stroke-ticks="true" data-units="mV"
					data-value-format="3.2" data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="0 250 #000, 250 400 #00cc00, 400 1000 #eaa"></canvas>
        </li>
        <li data-row="4" data-col="1" data-sizex="3" data-sizey="1">
        <H3 class="controls">Wave makers</H3>
        <div id="column1-wrap">
    <div id="column1">
    <canvas id="wave1"  width="100" height="100"
					data-type="canv-gauge" data-title="ORP" data-min-value="0"
					data-max-value="1000"
					data-major-ticks="0 100 200 300 400 500 600 700 800 900 1000"
					data-minor-ticks="50" data-stroke-ticks="true" data-units="mV"
					data-value-format="3.2" data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="0 250 #000, 250 400 #00cc00, 400 1000 #eaa"></canvas></div>
</div>
<div id="column2"><canvas id="wave2"  width="100" height="100"
					data-type="canv-gauge" data-title="ORP" data-min-value="0"
					data-max-value="1000"
					data-major-ticks="0 100 200 300 400 500 600 700 800 900 1000"
					data-minor-ticks="50" data-stroke-ticks="true" data-units="mV"
					data-value-format="3.2" data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="0 250 #000, 250 400 #00cc00, 400 1000 #eaa"></canvas></div>
<div id="clear"></div>
        </li>
        <li data-row="1" data-col="2" data-sizex="2" data-sizey="1">
        <H3 class="controls">Temperature history</H3>
        <canvas id="tempHistory" width="400" height="160"></canvas>
        </li>
        <li data-row="2" data-col="2" data-sizex="2" data-sizey="1">
        <H3 class="controls">Ph level history</H3>
        <canvas id="phHistory" width="400" height="160"></canvas>
        </li>
        <li data-row="3" data-col="2" data-sizex="2" data-sizey="1"></li>
        
        <li data-row="1" data-col="3" data-sizex="1" data-sizey="3">
        <H4 class="controls">Pump</H4>
        <div class="onoffswitch">
					<input type="checkbox" name="onoffswitch"
						class="onoffswitch-checkbox" id="myonoffswitch" checked> <label
						class="onoffswitch-label" for="myonoffswitch">
						<div class="onoffswitch-inner"></div>
						<div class="onoffswitch-switch"></div>
					</label>
				</div>
        </li>
    </ul>
</body>
</html>