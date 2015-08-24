<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Jaqua-pi aquarium controller</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div id="device_detail_page" data-role="page">
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
						<input type="checkbox" data-role="flipswitch" name="flip-checkbox-default_on_off" id="flip-checkbox-default_on_off" <c:if test="${device.mode.shouldBeOn()}">checked</c:if> />
					</form>
				</div>
			</div>
		</div>
		<!-- /content -->
		<script>
			$(document).on("pageinit", "#device_detail_page", function(event) {
				
			});
		</script>
	</div>
	<!-- /page -->

</body>
</html>