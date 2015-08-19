<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Jaqua-pi aquarium controller</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/app.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/jquery.gridster.min.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/toggle-switch.css' />" />

<script type="text/javascript" src="<c:url value='/s/Chart.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/gauge.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery-1.11.0.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery.gridster.min.js' />"></script>
<script type="text/javascript">
	$(function() { //DOM Ready
		$(".gridster > ul").gridster({
			widget_margins : [ 10, 10 ],
			widget_base_dimensions : [ 150, 200 ]
		}).data('gridster').disable();
	});
</script>
<script type="text/javascript" src="<c:url value='/s/temperature.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/ph.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/devices.js' />"></script>
</head>
<body>
	<div class="container">
		<H2 align="center">Aqua control</H2>
		<div class="gridster">
			<ul>
				<li data-row="1" data-col="1" data-sizex="1" data-sizey="1">
					<H4 class="controls">Temperature</H4>
					<canvas id="tempGuage"></canvas>
				</li>
				<li data-row="1" data-col="2" data-sizex="3" data-sizey="1">
					<H4 class="controls">Temperature Time line</H4>
					<canvas id="tempHistory" width="400" height="120"></canvas>
					<div class="toggle-container">
						<div class="switch-toggle switch-3 switch-candy">
							<input id="hour-tp" name="temperatureHistorySelection"
								type="radio" value="HOUR" checked /> <label for="hour-tp"
								onclick="">Hour</label> <input id="day-tp"
								name="temperatureHistorySelection" type="radio" value="DAY">
							<label for="day-tp" onclick="">Day</label> <input id="week-tp"
								name="temperatureHistorySelection" type="radio" value="WEEK">
							<label for="week-tp" onclick="">Week</label> <a></a>
						</div>
					</div>
				</li>

				<li data-row="2" data-col="1" data-sizex="1" data-sizey="1">
					<H4 class="controls">pH</H4>
					<canvas id="phGuage"></canvas>
				</li>
				<li data-row="2" data-col="2" data-sizex="3" data-sizey="1">
					<H4 class="controls">pH Time line</H4>
					<canvas id="phHistory" width="400" height="120"></canvas>
					<div class="toggle-container">
						<div class="switch-toggle switch-3  switch-candy">
							<input id="ph_hour-tp" name="phHistorySelection" type="radio"
								value="HOUR" checked />
							<label for="ph_hour-tp" onclick="">Hour</label>
							<input id="ph_day-tp" name="phHistorySelection" type="radio"
								value="DAY" />
							<label for="ph_day-tp" onclick="">Day</label>
							<input
								id="ph_week-tp" name="phHistorySelection" type="radio"
								value="WEEK" />
							<label for="ph_week-tp" onclick="">Week</label>
							<a></a>
						</div>
					</div>
				</li>
				<li data-row="1" data-col="3" data-sizex="1" data-sizey="2">
					<H4 class="controls">Devices</H4>
					<hr /> <c:forEach items="${deviceStatus}" var="element">
						<div>
							<img src="/i/device_${element.id}.png" alt="${element.name}"
								align="middle" />
						</div>
						<div class="onoffswitch">
							<input type="checkbox" name="onoffswitch_${element.id}"
								class="onoffswitch-checkbox" id="onoffswitch_${element.id}"
								<c:if test="${element.on}">checked</c:if> />
							<label
								class="onoffswitch-label" for="onoffswitch_${element.id}">
								<span class="onoffswitch-inner"></span> <span
								class="onoffswitch-switch"></span>
							</label>
						</div>
						<hr />
					</c:forEach>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>
