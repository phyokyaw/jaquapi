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
					<input type="radio" id="grid-radio-1" name="grid-radio-1" value="OFF">
				</div>
				<div class="ui-block-b">
					<label for="grid-radio-2">Auto</label>
					<input type="radio" id="grid-radio-2" name="grid-radio-1" value="AUTO">
				</div>
				<div class="ui-block-c">
					<label for="grid-radio-3">On</label>
					<input type="radio" id="grid-radio-3" name="grid-radio-1" value="ON">
					<a id="radio-3" href="#popupLogin" data-rel="popup" data-transition="slidedown" style='display:none;'></a>
				</div>
			</div>

			<div data-role="popup" id="popupLogin" data-theme="a"
				class="ui-corner-all" style="max-width: 100%" data-tolerance="0">
				<div style="padding: 10px 20px;">
					<h3>${device.name} control</h3>
					<label for="device_timout_minute_slider">Timer (minutes):</label>
					<input type="range" name="device_timout_minute_slider" id="device_timout_minute_slider" data-highlight="true" min="0" max="60" value="15">
					<label>
						<input type="checkbox" id="device_timeout_checkbox" name="device_timeout_checkbox">No timer
					</label>
					<a id="submit_device_change" href="#"
						class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-clock" data-rel="back"></a>
					<a href="#" class="ui-btn ui-corner-all ui-shadow ui-btn-b ui-btn-icon-left ui-icon-delete" data-rel="back">Cancel</a>
				</div>
			</div>
			<script>
				$("#grid-radio-3").click(function(event) {
					$("#submit_device_change").text("Turn On");
					$("#radio-3").click();
				});
				$("#grid-radio-1").click(function(event) {
					$("#submit_device_change").text("Turn Off");
					$("#radio-3").click();
				});
				$("#device_timeout_checkbox").change(function() {
					if($(this).is(':checked')) {
						$("#device_timout_minute_slider").slider("disable");
					} else {
						$("#device_timout_minute_slider").slider("enable");
					}
				});
				$("#submit_device_change").click(function() {
					var onOff;
					if ($('input[name=grid-radio-1]:checked').val() == "ON") {
						onOff = "true";
					} else {
						onOff = "false";
					}
					$.ajax({
						dataType : "json",
						url : "/device_override/${device.id}/" + onOff + "/" + $("#device_timout_minute_slider").val(),
						type : "GET",
						success : function(data) {
						},
						timeout : 1000
					});
				});
			</script>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>