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
	<div id="parameter_detail_page" data-role="page">
		<div data-role="header" style="overflow: hidden;">
			<h4>Aquarium control</h4>
			<a href="#" data-icon="gear"
				class="ui-btn-right ui-shadow ui-corner-all ui-btn-icon-notext">Setup</a>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home">Dushboard</a></li>
					<li><a href="/programmes" data-icon="action">Maintenance</a></li>
					<li><a href="/parameters" data-icon="star"
						class="ui-btn-active ui-state-persist">Params</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
			<h4 class="ui-bar ui-bar-a" align="center">${parameter.name} (${parameter.shortName})</h4>
			<%-- <canvas id="parameterHistory" height="150"></canvas> --%>
			<form action="/secure/add_parameter_record/" method="get">
				<label for="parameter_value">Test result</label>
				<input type="hidden" name="id" id="parameter_id" value="${parameter.id}" />
				<input type="number" data-clear-btn="true" name="value" pattern="[0-9]+([\.|,][0-9]+)?" step="0.01" id="parameter_value" value="" placeholder="Enter new value in ${parameter.unit}">
				<input type="submit" value="Record">
				<input type="reset" value="Reset">
			</form>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>