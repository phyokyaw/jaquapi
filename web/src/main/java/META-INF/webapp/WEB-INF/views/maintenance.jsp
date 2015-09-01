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
</head>
<body>
	<div data-role="page">
		<div data-role="header" style="overflow: hidden;">
			<h4>Maintenance</h4>
			<a href="#" data-icon="gear"
				class="ui-btn-right ui-shadow ui-corner-all ui-btn-icon-notext">Setup</a>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home">Dushboard</a></li>
					<li><a href="/feed_info" data-icon="star">Feed</a></li>
					<li><a href="#" data-icon="action"
						class="ui-btn-active ui-state-persist">Maintenance</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
			<ul data-role="listview"data-divider-theme="a" data-inset="true">
				<c:forEach items="${programmes}" var="element">
					<li data-role="list-divider">
					<h3>${element.name}</h3>
						<select id="test-slider" data-role="slider" name="testslider" data-mini="true">
							<option value="off">Start</option>
							<option value="on">Running</option>
						</select>
					</li>
					<c:forEach items="${element.devices}" var="programmeDevice">
						<li>
							${programmeDevice.device.name}
							<c:choose>
									<c:when test="${programmeDevice.shouldbeOff}">
										<span class="ui-li-count">OFF for ${programmeDevice.timeout} minutes.</span>
									</c:when>
									<c:otherwise>
										<span class="ui-li-count">ON for ${programmeDevice.timeout} minutes.</span>
									</c:otherwise>
								</c:choose>
						</li>
					</c:forEach>
				</c:forEach>
				
			</ul>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>