<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Jqua-pi aquarium controller</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/jquery.gridster.min.css' />">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/style.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/jquery-ui.min.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/jquery-ui.structure.min.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/jquery-ui.theme.min.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/toggle-switch.css' />" />
<script type="text/javascript" src="<c:url value='/s/gauge.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/Chart.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery-1.11.0.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery-ui.min.js' />"></script>
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
		var tempHistoryChart = new Chart(ctx);
		var tempData = [];

		var data = {
			labels : [ "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
					"14:00" ],
			datasets : [

			{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : [ 24.5, 25.0, 25.4, 24.8, 25.6, 25.7, 25.5 ]
			} ]
		};
		tempHistoryChart.Line(data);

		ctx = $("#phHistory").get(0).getContext("2d");
		//This will get the first returned node in the jQuery collection.
		myNewChart = new Chart(ctx);
		data = {
			labels : [ "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
					"14:00" ],
			datasets : [ {
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : [ 7.9, 7.8, 7.8, 7.9, 8.1, 8.0, 8.1 ]
			} ]
		}
		myNewChart.Line(data);
		ctx = $("#orpHistory").get(0).getContext("2d");
		//This will get the first returned node in the jQuery collection.
		myNewChart1 = new Chart(ctx);
		data = {
			labels : [ "8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
					"14:00" ],
			datasets : [ {
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : [ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 ]
			} ]
		}
		myNewChart1.Line(data);
	});
	$(function() {
		$("input[type=submit], a, button").button().click(function(event) {
			event.preventDefault();
		});
	});
	
	
	(function poll() {
		setTimeout(function() {
			$.ajax({
				dataType : "json",
				url : "/temperature_history?days=1",
				type : "GET",
				success : function(data) {
					tempHistoryChart.Line(data);
				},
				dataType : "json",
				complete : poll,
				timeout : 2000
			})
		}, 2000);
	})();
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
					data-highlights="15 20 #ff33aa, 20 24 #ffff00, 24 26.5 #00cc00, 26.5 28 #eaa, 28 30 #ff0000"></canvas>
			</li>
			<li data-row="2" data-col="1" data-sizex="1" data-sizey="1">
				<H3 class="controls">Ph Meter</H3>
				<canvas id="phGuage" width="150" height="150" data-type="canv-gauge"
					data-title="Ph" data-min-value="4" data-max-value="15"
					data-major-ticks="4 5 6 7 8 9 10 11 12 13 14 15"
					data-minor-ticks=".5" data-stroke-ticks="true" data-units="Level"
					data-value-format="3.2" data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="4 5 #ff0000, 5 7.7 #ffff00, 7.7 8.8 #00cc00, 8.8 15 #ff0000"></canvas>
			</li>
			<li data-row="3" data-col="1" data-sizex="1" data-sizey="1">
				<H3 class="controls">ORP Meter</H3>
				<canvas id="orpGuage" width="150" height="150"
					data-type="canv-gauge" data-title="ORP" data-min-value="0"
					data-max-value="1000"
					data-major-ticks="0 100 200 300 400 500 600 700 800 900 1000"
					data-minor-ticks="50" data-stroke-ticks="true" data-units="mV"
					data-value-format="3.2" data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="0 250 #000, 250 400 #00cc00, 400 1000 #eaa"></canvas>
			</li>
			<li data-row="4" data-col="1" data-sizex="1" data-sizey="1">
				<H3 class="controls">Salinity</H3>
				<canvas id="salinityGuage" width="150" height="150"
					data-type="canv-gauge" data-title="ORP" data-min-value="0"
					data-max-value="1000"
					data-major-ticks="0 100 200 300 400 500 600 700 800 900 1000"
					data-minor-ticks="50" data-stroke-ticks="true" data-units="mV"
					data-value-format="3.2" data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="0 250 #000, 250 400 #00cc00, 400 1000 #eaa"></canvas>
			</li>
			<li data-row="1" data-col="2" data-sizex="2" data-sizey="1">
				<H3 class="controls">Temperature history</H3>
				<canvas id="tempHistory" width="400" height="130"></canvas>
				<div class="toggle-container">
					<div class="switch-toggle switch-3">
						<input id="day-tp" name="temperatureHistorySelection" type="radio"
							checked> <label for="day-tp" onclick="">Day</label> <input
							id="week-tp" name="temperatureHistorySelection" type="radio">
						<label for="week-tp" onclick="">Week</label> <input id="month-tp"
							name="temperatureHistorySelection" type="radio"> <label
							for="month-tp" onclick="">Month</label> <a></a>
					</div>
				</div>
			</li>
			<li data-row="2" data-col="2" data-sizex="2" data-sizey="1">
				<H3 class="controls">Ph level history</H3>
				<canvas id="phHistory" width="400" height="130"></canvas>
				<div class="toggle-container">
					 <div class="switch-toggle switch-3">
                <input id="week-d1" name="view-d" type="radio" checked>
                <label for="week-d1" onclick="">Week</label>

                <input id="month-d2" name="view-d" type="radio">
                <label for="month-d2" onclick="">Month</label>

                <input id="month-d3" name="view-d" type="radio">
                <label for="month-d3" onclick="">Year</label>

                <a></a>
              </div>
				</div>
			</li>
			<li data-row="3" data-col="2" data-sizex="2" data-sizey="1">
				<H3 class="controls">ORP history</H3>
				<canvas id="orpHistory" width="400" height="130"></canvas>
				<div class="toggle-container">
					<div class="switch-toggle switch-3">
						<input id="day-orp" name="orpHistorySelection" type="radio"
							checked> <label for="day-orp" onclick="">Day</label> <input
							id="week-orp" name="orpHistorySelectionWeek" type="radio">
						<label for="week-orp" onclick="">Week</label> <input
							id="month-orp" name="orpHistorySelectionMonth" type="radio">
						<label for="month-orp" onclick="">Month</label> <a></a>
					</div>
				</div>
			</li>
			<li data-row="4" data-col="2" data-sizex="2" data-sizey="1">
				<H3 class="controls">Salinity history</H3>
				<canvas id="salinityHistory" width="400" height="130"></canvas>
				<div class="toggle-container">
					<div class="switch-toggle switch-3">
						<input id="day-orp" name="salinityHistorySelection" type="radio"
							checked> <label for="day-orp" onclick="">Day</label> <input
							id="week-orp" name="salinityHistorySelectionWeek" type="radio">
						<label for="week-orp" onclick="">Week</label> <input
							id="month-orp" name="salinityHistorySelectionMonth" type="radio">
						<label for="month-orp" onclick="">Month</label> <a></a>
					</div>
				</div>
			</li>
			<li data-row="1" data-col="3" data-sizex="1" data-sizey="3">
				<H3 class="controls">Power controls</h3>
				<c:forEach
					items="${devices}" var="element">
					<H4 class="controls">${element.name}</H4>
					<div class="onoffswitch">
						<input type="checkbox" name="onoffswitch"
							class="onoffswitch-checkbox" id="myonoffswitch-${element.id}"
							<c:if test="${element.mode.shouldBeOn()}">checked</c:if>>
						<label class="onoffswitch-label"
							for="myonoffswitch-${element.id}">
							<div class="onoffswitch-inner"></div>
							<div class="onoffswitch-switch"></div>
						</label>
					</div>
				</c:forEach>
			</li>
			<li data-row="1" data-col="4" data-sizex="1" data-sizey="3">
				<H3 class="controls">Programmes</h3> <c:forEach
					items="${programmes}" var="element">
					<button class="controls">${element.name}</button>
				</c:forEach>
			</li>
		</ul>
	</div>
</body>
</html>
