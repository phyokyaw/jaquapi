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
<script type="text/javascript" src="<c:url value='/s/devices.js' />"></script>
</head>
<body>
	<div id="device_detail_page" data-role="page">
		<div data-role="header" data-add-back-btn="true"
			style="overflow: hidden;">
			<h4>Aquarium control</h4>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home"
						class="ui-btn-active ui-state-persist">Dushboard</a></li>
					<li><a href="/programmes" data-icon="action">Maintenance</a></li>
					<li><a href="/parameters" data-icon="star">Params</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
			<h4 class="ui-bar ui-bar-a" align="center">${device.name}</h4>
			<div class="ui-grid-b">
				<div class="ui-block-a" style="width: 20%">
					<img src="/i/device_${device.id}.png" />
				</div>
				<div class="ui-block-b" style="width: 60%">
					<p>${device.mode.formattedInfo}</p>
				</div>
				<div class="ui-block-c" style="width: 20%">
					<p id="device_status"></p>
				</div>
			</div>
			<div class="ui-grid-b">
				<div class="ui-block-a">
					<a id="device_programme_off" href="#popupDialog" data-rel="popup"
						class="ui-shadow ui-btn ui-corner-all">OFF</a>
				</div>
				<div class="ui-block-b">
					<a id="device_programme_auto" href="#"
						class="ui-shadow ui-btn ui-corner-all">AUTO</a>
				</div>
				<div class="ui-block-c">
					<a id="device_programme_on" href="#popupDialog" data-rel="popup"
						class="ui-shadow ui-btn ui-corner-all">ON</a>
				</div>
			</div>

			<div data-role="popup" id="popupDialog" data-theme="a"
				class="ui-corner-all" style="max-width: 100%" data-tolerance="0">
				<div style="padding: 10px 20px;">
					<h3>${device.name} control</h3>
					<label for="device_timout_minute_slider">Timer (minutes):</label> <input
						type="range" name="device_timout_minute_slider"
						id="device_timout_minute_slider" data-highlight="true" min="0"
						max="60" value="15"> <label> <input
						type="checkbox" id="device_timeout_checkbox"
						name="device_timeout_checkbox">No timer
					</label> <input id="device_detail_id" type="hidden" value="${device.id}" />
					<a id="submit_device_change" href="#"
						class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-clock"
						data-rel="back"></a> <a href="#"
						class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-delete"
						data-rel="back">Cancel</a>
				</div>
			</div>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>