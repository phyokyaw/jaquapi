<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Gtest</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/jquery.gridster.min.css' />">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/desk.css' />">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/style.css' />" />
<script type="text/javascript" src="<c:url value='/s/gauge.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery-1.11.0.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery.gridster.min.js' />"></script>
<script>
	$(function() { //DOM Ready
		$(".gridster > ul").gridster({
			widget_margins : [ 10, 10 ],
			widget_base_dimensions : [ 300, 250 ]
		}).data('gridster');
	});
	(function poll() {
		setTimeout(function() {
			$.ajax({
				dataType : "json",
				url : "/temperature",
				type : "GET",
				success : function(data) {
					Gauge.Collection.get('tempGuage').setValue(data.value);
				},
				dataType : "json",
				complete : poll,
				timeout : 2000
			})
		}, 2000);
	})();
</script>
<script>
	!function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (!d.getElementById(id)) {
			js = d.createElement(s);
			js.id = id;
			js.src = "//platform.twitter.com/widgets.js";
			fjs.parentNode.insertBefore(js, fjs);
		}
	}(document, "script", "twitter-wjs");
</script>
</head>

<body>
	<H1 align="center">Aqua control</H1>
	<div class="gridster">
		<ul>
			<li data-row="1" data-col="1" data-sizex="1" data-sizey="3">
				<h1>Maintenance</h1>
				<h2>Water change</h2> <span class="switch"> <span
					class="switch-border1"> <span class="switch-border2">
							<input id="switch1" type="checkbox" checked /> <label
							for="switch1"></label> <span class="switch-top"></span> <span
							class="switch-shadow"></span> <span class="switch-handle"></span>
							<span class="switch-handle-left"></span> <span
							class="switch-handle-right"></span> <span
							class="switch-handle-top"></span> <span
							class="switch-handle-bottom"></span> <span
							class="switch-handle-base"></span> <span
							class="switch-led switch-led-green"> <span
								class="switch-led-border"> <span class="switch-led-light">
										<span class="switch-led-glow"></span>
								</span>
							</span>
						</span> <span class="switch-led switch-led-red"> <span
								class="switch-led-border"> <span class="switch-led-light">
										<span class="switch-led-glow"></span>
								</span>
							</span>
						</span>
					</span>
				</span>
			</span>
				<h2>Filter cleaning</h2>
				<div class="onoffswitch">
					<input type="checkbox" name="onoffswitch"
						class="onoffswitch-checkbox" id="filterCleaning" checked>
					<label class="onoffswitch-label" for="filterCleaning">
						<div class="onoffswitch-inner"></div>
						<div class="onoffswitch-switch"></div>
					</label>
				</div>
			</li>
			<li data-row="1" data-col="2" data-sizex="1" data-sizey="1">
				<H2>Temperature</H2>
				<canvas id="tempGuage" width="200" height="200"
					data-type="canv-gauge" data-title="Temp" data-min-value="15"
					data-max-value="30"
					data-major-ticks="15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30"
					data-minor-ticks=".5" data-stroke-ticks="true" data-units="C&deg;"
					data-value-format="3.2" data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="15 20 #ff0000, 20 23 #ffff00, 23 28 #00cc00, 28 30 #eaa"></canvas>
			</li>
			<li data-row="2" data-col="2" data-sizex="1" data-sizey="1">
				<H2>Ph Meter</H2>
				<canvas id="phGuage" width="200" height="200" data-type="canv-gauge"
					data-title="Ph" data-min-value="4" data-max-value="12"
					data-major-ticks="4 5 6 7 8 9 10 11 12" data-minor-ticks=".5"
					data-stroke-ticks="true" data-units="Level" data-value-format="3.2"
					data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="4 5 #ff0000, 5 7.7 #ffff00, 7.7 8.8 #00cc00, 8.8 12 #eaa"></canvas>
			</li>
			<li data-row="3" data-col="2" data-sizex="1" data-sizey="1">
				<H2>ORP Meter</H2>
				<canvas id="orpGuage" width="200" height="200"
					data-type="canv-gauge" data-title="ORP" data-min-value="0"
					data-max-value="1000"
					data-major-ticks="0 100 200 300 400 500 600 700 800 900 1000"
					data-minor-ticks="50" data-stroke-ticks="true" data-units="mV"
					data-value-format="3.2" data-glow="true" data-animation-delay="10"
					data-animation-duration="200" data-animation-fn="bounce"
					data-colors-needle="#f00 #00f"
					data-highlights="0 250 #000, 250 400 #00cc00, 400 1000 #eaa"></canvas>
			</li>
			<li data-row="1" data-col="3" data-sizex="1" data-sizey="3">
				<h1>Power status</h1>
				<h2>Skimmer</h2>
				<div class="onoffswitch">
					<input type="checkbox" name="onoffswitch"
						class="onoffswitch-checkbox" id="myonoffswitch" checked> <label
						class="onoffswitch-label" for="myonoffswitch">
						<div class="onoffswitch-inner"></div>
						<div class="onoffswitch-switch"></div>
					</label>
				</div>
				<h2>Pump</h2>
				<div class="onoffswitch">
					<input type="checkbox" name="onoffswitch"
						class="onoffswitch-checkbox" id="myonoffswitch1" checked>
					<label class="onoffswitch-label" for="myonoffswitch1">
						<div class="onoffswitch-inner"></div>
						<div class="onoffswitch-switch"></div>
					</label>
				</div>
				<h2>Wave maker 1</h2>
				<div class="onoffswitch">
					<input type="checkbox" name="onoffswitch"
						class="onoffswitch-checkbox" id="myonoffswitch2" checked>
					<label class="onoffswitch-label" for="myonoffswitch2">
						<div class="onoffswitch-inner"></div>
						<div class="onoffswitch-switch"></div>
					</label>
				</div>
				<h2>Wave maker 2</h2>
				<div class="onoffswitch">
					<input type="checkbox" name="onoffswitch"
						class="onoffswitch-checkbox" id="myonoffswitch3" checked>
					<label class="onoffswitch-label" for="myonoffswitch3">
						<div class="onoffswitch-inner"></div>
						<div class="onoffswitch-switch"></div>
					</label>
				</div>
				<h2>Media Reactor</h2>
				<div class="onoffswitch">
					<input type="checkbox" name="onoffswitch"
						class="onoffswitch-checkbox" id="myonoffswitch4" checked>
					<label class="onoffswitch-label" for="myonoffswitch4">
						<div class="onoffswitch-inner"></div>
						<div class="onoffswitch-switch"></div>
					</label>
				</div>
			</li>
		</ul>
	</div>

</body>
</html>