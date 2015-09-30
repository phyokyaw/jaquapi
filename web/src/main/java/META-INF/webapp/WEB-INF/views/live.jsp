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
<script type="text/javascript" src="<c:url value='/s/jsmpg.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/gauge.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/Chart.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/devices.js' />"></script>
</head>
<body>
	<div id="ph_timeline_page" data-role="page" style="overflow: visible;">
		<div data-role="header" style="overflow: visible;">
			<h4>Aquarium control</h4>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home">Dashboard</a></li>
					<li><a href="/programmes" data-icon="action">Maintenance</a></li>
					<li><a href="/parameters" data-icon="star">Params</a></li>
					<li><a href="/live" class="ui-btn-active ui-state-persist"
						data-icon="camera">Live Cam</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content" style="overflow: visible;">
			<div class="center-wrapper" style="overflow: visible;">
				<h4 class="ui-bar ui-bar-a">Live Cam</h4>
				<canvas id="videoCanvas" width="640" height="480">
					<p>Video not support</p>
				</canvas>
				<script type="text/javascript">
					// Setup the WebSocket connection and start the player
					var client = new WebSocket('ws://phyokyaw.net:8084/');
					var canvas = document.getElementById('videoCanvas');
					var player = new jsmpeg(client, {
						canvas : canvas
					});
				</script>
			</div>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->
</body>
</html>