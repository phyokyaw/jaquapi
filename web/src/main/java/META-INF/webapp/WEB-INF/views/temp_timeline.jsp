<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Jaqua-pi aquarium controller</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div id="temp_timeline_page" data-role="page">
		<div data-role="header" style="overflow: hidden;" data-add-back-btn="true">
			<h4>Temperature</h4>
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
			<div class="center-wrapper">
				<h4>Time line</h4>
				<canvas id="tempHistory" height="150"></canvas>
			</div>
			<form>
			<fieldset data-role="controlgroup" data-type="horizontal">
			    <input type="radio" name="temperatureHistorySelection" id="radio-choice-h-2a" value="HOUR" checked="checked">
			    <label for="radio-choice-h-2a">Hour</label>
			    <input type="radio" name="temperatureHistorySelection" id="radio-choice-h-2b" value="DAY">
			    <label for="radio-choice-h-2b">Day</label>
			    <input type="radio" name="temperatureHistorySelection" id="radio-choice-h-2c" value="WEEK">
			    <label for="radio-choice-h-2c">Week</label>
			</fieldset>
			</form>
		</div>
		<!-- /content -->
		
		<script type="text/javascript">
		var tempHistoryChart;
  		var temp_interval = 'HOUR';
		function tempHistoryPoll(recall) {		
			$.ajax({
				dataType : "json",
				url : "/temperature_history?interval=" + temp_interval,
				type : "GET",
				success : function(tempData) {	
					var tdata = {
						labels : tempData.labels,
						datasets : [
						{
							fillColor : "rgba(151,187,205,0.5)",
							strokeColor : "rgba(151,187,205,1)",
							pointColor : "rgba(151,187,205,1)",
							pointStrokeColor : "#fff",
							data : tempData.values,
						}]
					};
					options = {
						animation : false
					}
					if (tempHistoryChart != null) {
						tempHistoryChart.destroy();
					}
					var ctx = $("#tempHistory").get(0).getContext("2d");
					$("#tempHistory").attr('width', $("#tempHistory").parent().parent().width());
					tempHistoryChart = new Chart(ctx).Line(tdata, options);
					if (tempHistoryChart == null) {
						alert( "This chart was just enhanced by jQuery Mobile!" );
					}
				},
				complete : function() {
					if (recall) {
						setTimeout(function() {tempHistoryPoll(true)}, 10000)
					}
				},
				timeout : 1000
			});
		}
		$(document).on("pageshow", "#temp_timeline_page", function(event) {
			tempHistoryPoll(true);
			$("[name=temperatureHistorySelection]").change(function() {
				temp_interval = $(this).val();
				tempHistoryPoll(false);
			});
		});
        </script>
	</div>
	<!-- /page -->
</body>
</html>