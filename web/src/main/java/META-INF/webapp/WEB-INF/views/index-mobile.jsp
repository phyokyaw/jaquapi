<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Aquarium controller</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="mobile-web-app-capable" content="yes">
	<link rel="stylesheet" type="text/css"
		href="<c:url value='/c/jquery.mobile-1.4.5.min.css' />" />
	<link rel="stylesheet" type="text/css"
		href="<c:url value='/c/mobile.css' />" />
	<script type="text/javascript"
		src="<c:url value='/s/jquery-1.11.0.min.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/s/jquery.mobile-1.4.5.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/s/gauge.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/s/Chart.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/s/devices.js' />"></script>
	<link rel="apple-touch-icon" href="/i/app-icon-iphone.png">
	<link rel="apple-touch-icon" sizes="72x72" href="/i/app-icon-ipad.png">
	<link rel="apple-touch-icon" sizes="114x114" href="/i/app-icon-iphone-retina.png">
	<link rel="apple-touch-icon" sizes="144x144" href="/i/app-icon-ipad-retina.png">
</head>
<body>
	<div id="dashboard" data-role="page">
		<div data-role="header" style="overflow: hidden;">
			<h4>Aquarium control</h4>
			<a href="/secure/setup" data-icon="gear"
				class="ui-btn-right ui-shadow ui-corner-all ui-btn-icon-notext">Setup</a>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home"
						class="ui-btn-active ui-state-persist">Dashboard</a></li>
					<li><a href="/programmes" data-icon="action">Maintenance</a></li>
					<li><a href="/parameters" data-icon="star">Params</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
			<div class="center-wrapper">
				<div class="ui-grid-a">
					<div class="ui-block-a">
						<div class="ui-bar ui-bar-a">
							<p>Temp</p>
							<canvas id="tempGuage"></canvas>
							<a href="/temp_timeline"
								class="ui-btn ui-btn-b ui-mini ui-corner-all ui-btn-icon-right ui-icon-info ui-btn-inline">Timeline</a>
						</div>
					</div>
					<div class="ui-block-b">
						<div class="ui-bar ui-bar-a">
							<p>pH</p>
							<canvas id="phGuage"></canvas>
							<a href="/ph_timeline"
								class="ui-btn ui-btn-b ui-mini ui-corner-all ui-btn-icon-right ui-icon-info ui-btn-inline">Timeline</a>
						</div>
					</div>
				</div>
			</div>

			<!-- Sensors -->
			<div data-role="collapsible" data-collapsed="false">
				<h4>Sensors</h4>
				<ul data-role="listview">
					<c:forEach items="${sensorDevices}" var="element">
						<li><a href="/sensor_detail/${element.id}"> <img
								class="ui-li-icon ui-corner-none"
								src="/i/sensor_${element.id}_small.png" />
								${element.name}
								<c:choose>
									<c:when test="${element.onError}">
										<span id="sensor_${element.id}_switch_status" class="ui-li-count"
											style="color: white; background-color: red">${element.onErrorMessage}</span>
									</c:when>
									<c:otherwise>
										<span id="sensor_${element.id}_switch_status" class="ui-li-count"
											style="color: white; background-color: green">OK</span>
									</c:otherwise>
								</c:choose>
						</a></li>
					</c:forEach>
				</ul>
			</div>

			<!-- Devices -->
			<div data-role="collapsible" data-collapsed="false">
				<h4>Devices</h4>
				<ul data-role="listview">
					<c:forEach items="${deviceStatus}" var="element">
						<li><a href="/device/${element.id}"> <img
								class="ui-li-icon"
								src="/i/device_${element.id}.png" />
								<h2>${element.name}</h2>
								<p>${element.modeInfo}</p>
								<c:choose>
									<c:when test="${element.overridden}">
										<c:choose>
											<c:when test="${element.on}">
												<span id="device_${element.id}_switch_status" class="ui-li-count"
													style="color: white; background-color: blue">ON (${element.overridingModeTimeout})</span>
											</c:when>
											<c:otherwise>
													<span id="device_${element.id}_switch_status" class="ui-li-count"
											style="color: white; background-color: black">OFF (${element.overridingModeTimeout}</span>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${element.on}">
												<span id="device_${element.id}_switch_status" class="ui-li-count"
													style="color: white; background-color: blue">ON (AUTO)</span>
											</c:when>
											<c:otherwise>
													<span id="device_${element.id}_switch_status" class="ui-li-count"
											style="color: white; background-color: black">OFF (AUTO)</span>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
						</a></li>
					</c:forEach>
				</ul>
			</div>

		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>