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
	<div id="temp_timeline_page" data-role="page">
		<div data-role="header" style="overflow: hidden;" data-add-back-btn="true">
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
			<div class="center-wrapper">
				<h4 class="ui-bar ui-bar-a">Temperature Time line</h4>
				<canvas id="tempHistory" height="150"></canvas>
			</div>
			<form>
			<fieldset data-role="controlgroup" data-type="horizontal">
			    <input type="radio" name="temperatureHistorySelection" id="radio-choice-h-2a" value="HOUR" checked="checked">
			    <label for="radio-choice-h-2a">Hour</label>
			    <input type="radio" name="temperatureHistorySelection" id="radio-choice-h-2b" value="DAY">
			    <label for="radio-choice-h-2b">Day</label>
			    <input type="radio" name="temperatureHistorySelection" id="radio-choice-h-2c" value="WEEK">
			    <label for="radio-choice-h-2c">Week</label>
			</fieldset>
			</form>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->
</body>
</html>