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
	<script type="text/javascript" src="<c:url value='/s/devices.js' />"></script>
</head>
<body>
	<div id="ph_timeline_page" data-role="page" style="overflow: visible;">
		<div data-role="header" data-add-back-btn="true" style="overflow: visible;">
			<h4>Aquarium control</h4>
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
		<div role="main" class="ui-content" style="overflow: visible;">
			<div class="center-wrapper" style="overflow: visible;">
				<h4 class="ui-bar ui-bar-a">pH Time line</h4>
				<canvas id="phHistory" height="150" style="overflow: visible;"></canvas>
			</div>
			<form>
			<fieldset data-role="controlgroup" data-type="horizontal">
			    <input type="radio" name="phHistorySelection" id="phHistorySelection_hour" value="HOUR" checked="checked">
			    <label for="phHistorySelection_hour">Hour</label>
			    <input type="radio" name="phHistorySelection" id="phHistorySelection_day" value="DAY">
			    <label for="phHistorySelection_day">Day</label>
			    <input type="radio" name="phHistorySelection" id="phHistorySelection_week" value="WEEK">
			    <label for="phHistorySelection_week">Week</label>
			</fieldset>
			</form>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->
</body>
</html>