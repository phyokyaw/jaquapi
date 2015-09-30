<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Aquarium controller</title>
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
	<div id="programme_detail_page" data-role="page">
		<div data-role="header" style="overflow: hidden;"
			data-add-back-btn="true">
			<h4>Aquarium control</h4>
			<a href="#" data-icon="gear"
				class="ui-btn-right ui-shadow ui-corner-all ui-btn-icon-notext">Setup</a>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home">Dashboard</a></li>
					<li><a href="/programmes" data-icon="action"
						class="ui-btn-active ui-state-persist">Maintenance</a></li>
					<li><a href="/parameters" data-icon="star">Params</a></li>
					<li><a href="/live"
						data-icon="camera">Live Cam</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
			<input id="programme_detail_id" type="hidden" value="${programmeStatus.id}" />
			<a id="activate_programme" href="/secure/activate_programme/${programmeStatus.id}" class="ui-btn ui-btn-b" >Do ${programmeStatus.name}</a>
			<ul data-role="listview" data-inset="true">
				<c:forEach items="${programmeStatus.programmeDevices}" var="element">
					<li><img class="ui-li-icon"
						src="/i/device_${element.device.id}.png" />
						<h2>${element.device.name}</h2> <c:choose>
							<c:when test="${element.shouldbeOn}">
								<p>ON for ${element.timeout} minutes.</p>
							</c:when>
							<c:otherwise>
								<p>OFF for ${element.timeout} minutes.</p>
							</c:otherwise>
						</c:choose> <c:choose>
							<c:when test="${element.deviceStatus.overridden}">
								<c:choose>
									<c:when test="${element.deviceStatus.on}">
										<span id="programme_device_${element.deviceStatus.id}_switch_status"
											class="ui-li-count"
											style="color: white; background-color: blue">ON
											(${element.deviceStatus.overridingModeTimeout})</span>
									</c:when>
									<c:otherwise>
										<span id="programme_device_${element.deviceStatus.id}_switch_status"
											class="ui-li-count"
											style="color: white; background-color: black">OFF
											(${element.deviceStatus.overridingModeTimeout}</span>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${element.deviceStatus.on}">
										<span id="programme_device_${element.deviceStatus.id}_switch_status"
											class="ui-li-count"
											style="color: white; background-color: blue">ON (AUTO)</span>
									</c:when>
									<c:otherwise>
										<span id="programme_device_${element.deviceStatus.id}_switch_status"
											class="ui-li-count"
											style="color: white; background-color: black">OFF
											(AUTO)</span>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose></li>
				</c:forEach>
			</ul>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>