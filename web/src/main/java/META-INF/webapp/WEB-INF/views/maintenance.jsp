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
	<div data-role="page">
		<div data-role="header" style="overflow: hidden;">
			<h4>Aquarium control</h4>
			<a href="#" data-icon="gear"
				class="ui-btn-right ui-shadow ui-corner-all ui-btn-icon-notext">Setup</a>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home">Dushboard</a></li>
					<li><a href="/programmes" data-icon="action"
						class="ui-btn-active ui-state-persist">Maintenance</a></li>
					<li><a href="/parameters" data-icon="star">Params</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
			<h4 class="ui-bar ui-bar-a" align="center">Maintenance</h4>
			<ul data-role="listview" data-inset="true">
				<c:forEach items="${programmesStatus}" var="element">
					<li><a href="programme_detail/${element.id}">
						<%-- <img src="images/programme_${element.id}.png" /> --%>
						<h3 class="ui-bar ui-bar-a ui-corner-all">${element.name}</h3>
						<p><b>${element.programmeStatus}</b></p>
					</a>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>