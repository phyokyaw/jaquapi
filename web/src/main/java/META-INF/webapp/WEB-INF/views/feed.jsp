<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Jaqua-pi aquarium controller</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
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
	<script type="text/javascript" src="<c:url value='/s/temperature.js' />"></script>
	<script type="text/javascript" src="<c:url value='/s/sensors.js' />"></script>
	<script type="text/javascript" src="<c:url value='/s/ph.js' />"></script>
	<script type="text/javascript" src="<c:url value='/s/devices.js' />"></script>
</head>
<body>
	<div data-role="page">
		<div data-role="header" style="overflow: hidden;">
			<h4>Feed</h4>
			<a href="#" data-icon="gear"
				class="ui-btn-right ui-shadow ui-corner-all ui-btn-icon-notext">Setup</a>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home">Dushboard</a></li>
					<li><a href="#" data-icon="star"
						class="ui-btn-active ui-state-persist">Feed</a></li>
					<li><a href="/programmes" data-icon="action">Maintenance</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
			<ul data-role="listview" data-inset="true">
				<li data-role="list-divider">
					<div class="ui-bar ui-grid-a">
						<div class="ui-block-a" style="margin-top: 10px;">Controls</div>
						<div class="ui-block-b" style="text-align: right;">
							<select id="test-slider" data-role="slider" name="testslider"
								data-mini="true">
								<option value="off">Start</option>
								<option value="on">Running</option>
							</select>
						</div>
					</div>
				</li>
				<c:forEach items="${programme.devices}" var="programmeDevice">
					<li>
						<form class="ui-field-contain">
							<label for="programmeDevice_timeout_${programmeDevice.device.id}">${programmeDevice.device.name}
								<c:choose>
									<c:when test="${programmeDevice.shouldbeOff}">
										<b>OFF</b>
									</c:when>
									<c:otherwise>
										<b>ON</b>
									</c:otherwise>
								</c:choose> for
							</label><input type="range"
								name="programmeDevice_timeout_${programmeDevice.device.id}"
								id="programmeDevice_timeout_${programmeDevice.device.id}"
								data-highlight="true" min="0" step="5" max="30"
								value="${programmeDevice.timeout}" />
						</form>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>