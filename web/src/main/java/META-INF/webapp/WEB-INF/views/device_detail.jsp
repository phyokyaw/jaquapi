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
			<div class="ui-grid-b">
				<div class="ui-block-a" style="width: 30%">
					<img src="/i/device_${device.id}.png" />
				</div>
				<div class="ui-block-b" style="width: 70%">
					<p>${device.mode.formattedInfo}</p>
				</div>
			</div>
			<div class="ui-grid-b">
				<div class="ui-block-a">
					<label for="grid-radio-1">OFF</label>
					<input type="radio" id="grid-radio-1" name="grid-radio-1">
				</div>
				<div class="ui-block-b">
					<label for="grid-radio-2">Auto</label>
					<input type="radio" id="grid-radio-2" name="grid-radio-1">
				</div>
				<div class="ui-block-c">
					<label for="grid-radio-3">On</label>
					<input type="radio" id="grid-radio-3" name="grid-radio-1">
					<a id="radio-3" href="#popupLogin" data-rel="popup" data-transition="slidedown" style='display:none;'></a>
				</div>
			</div>
			<script>
				$("#grid-radio-3").click(function(event) {
					$("#radio-3").click();
				});
				$("#grid-radio-1").click(function(event) {
					$("#radio-3").click();
				});
			</script>
			<div data-role="popup" id="popupLogin" data-theme="a"
				class="ui-corner-all" style="max-width: 100%" data-tolerance="0">
				<form>
					<div style="padding: 10px 20px;">
						<label for="slider-2">Delay in minutes:</label>
						<input type="range" name="slider-2" id="slider-2" data-highlight="true" min="0" max="60" value="15" step="5">
						<label>
							<input type="checkbox" name="checkbox-0">No time out
						</label>
						<button type="submit"
							class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-clock">Override</button>
						<a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-delete" data-rel="back">Cancel</a>
					</div>
				</form>
			</div>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>