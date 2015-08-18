<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Jqua-pi aquarium controller</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/app.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/jquery.gridster.min.css' />" />
<script type="text/javascript" src="<c:url value='/s/Chart.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/gauge.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery-1.11.0.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery.gridster.min.js' />"></script>
<script type="text/javascript">
	$(function() { //DOM Ready
		$(".gridster > ul").gridster({
			widget_margins : [ 10, 10 ],
			widget_base_dimensions : [ 200, 200 ]
		}).data('gridster').disable();
	});
</script>
<script type="text/javascript" src="<c:url value='/s/temperature.js' />"></script>
</head>
<body>
	<div class="container">
		<H2 align="center">Aqua control</H2>
		<div class="gridster">
			<ul>
				<li data-row="1" data-col="1" data-sizex="1" data-sizey="1">
					<H4 class="controls">Temperature</H4>
					<canvas id="tempGuage"></canvas>
				</li>
				<li data-row="1" data-col="2" data-sizex="2" data-sizey="1">
					<H4 class="controls">Temperature Time line</H4>
					<canvas id="tempHistory" width="400" height="150"></canvas>
				</li>
				<li data-row="1" data-col="3" data-sizex="1" data-sizey="2">
					<H4 class="controls">Devices</H4>
					<hr/>
					<c:forEach items="${devices}" var="element">
						<div>
							<img src="/i/device_${element.id}.png" alt="${element.name}" align="middle" />
						</div>
						<div class="onoffswitch">
							<input type="checkbox" name="onoffswitch_${element.id}"
								class="onoffswitch-checkbox" id="onoffswitch_${element.id}" <c:if test="${element.mode.shouldBeOn()}">checked</c:if>>
							<label class="onoffswitch-label" for="onoffswitch_${element.id}"> <span
								class="onoffswitch-inner"></span> <span
								class="onoffswitch-switch"></span>
							</label>
						</div>
						<hr/>
					</c:forEach>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>
