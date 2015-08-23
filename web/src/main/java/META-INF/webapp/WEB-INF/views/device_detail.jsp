<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<div data-role="header" data-add-back-btn="true"
			style="overflow: hidden;">
			<h4>${device.name}</h4>
			<div data-role="navbar">
				<ul>
					<li><a href="#" data-icon="home"
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
				<div class="ui-block-a" style="width:30%"><img src="/i/device_${device.id}.png" /></div>
				<div class="ui-block-b" style="width:70%"><p>${device.mode.formattedInfo}</p></div>
			</div>
			<div class="ui-grid-a">
				<div class="ui-block-a" style="width:70%">
						<input type="range"
							name="device_timeout" id="device_timeout" data-highlight="true" min="0"
							step="5" max="60" value="20" />
				</div>
				<div class="ui-block-b" style="width:30%">
					<form>
						<input type="checkbox" data-role="flipswitch" name="flip-checkbox-feed" id="flip-checkbox-feed" <c:if test="${device.mode.shouldBeOn()}">checked</c:if> />
					</form>
				</div>
			</div>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>