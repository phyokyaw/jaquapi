<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Jaqua-pi aquarium controller</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div id="device_detail_page" data-role="page">
		<div data-role="header" data-add-back-btn="true"
			style="overflow: hidden;">
			<h4>${sensor.name}</h4>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home"
						class="ui-btn-active ui-state-persist">Dushboard</a></li>
					<li><a href="/feed_info" data-icon="star">Feed</a></li>
					<li><a href="/programmes" data-icon="action">Maintenance</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
			<div class="ui-grid-a">
				<div class="ui-block-a" style="width:30%"><img src="/i/sensor_${sensor.id}.png" /></div>
				<div class="ui-block-b" style="width:70%"><p>${sensor.description}</p></div>
			</div>
			<div align="center">
				<c:choose>
					<c:when test="${sensor.onError}">
						<h3 id="sensor_detail_${sensor.id}_switch_status" class="ui-bar ui-bar-a" style="color: red">${sensor.onErrorMessage}</h3>
					</c:when>
					<c:otherwise>
						<h3 id="sensor_detail_${sensor.id}_switch_status" class="ui-bar ui-bar-a" style="color: green">OK</h3>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<!-- /page -->
</body>
</html>