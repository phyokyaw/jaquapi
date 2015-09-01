<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Jaqua-pi aquarium controller</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div id="ph_timeline_page" data-role="page">
		<div data-role="header" style="overflow: hidden;" data-add-back-btn="true">
			<h4>pH</h4>
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
				<canvas id="phHistory" height="150"></canvas>
			</div>
			<form>
			<fieldset data-role="controlgroup" data-type="horizontal">
			    <input type="radio" name="phHistorySelection" id="phHistorySelection_hour" value="HOUR" checked="checked">
			    <label for="phHistorySelection_hour">Hour</label>
			    <input type="radio" name="phHistorySelection" id="phHistorySelection_day" value="DAY">
			    <label for="phHistorySelection_day">Day</label>
			    <input type="radio" name="phHistorySelection" id="phHistorySelection_week" value="WEEK">
			    <label for="phHistorySelection_week">Week</label>
			</fieldset>
			</form>
		</div>
		<!-- /content -->
		<script>
			var phHistoryChart;
			var ph_interval = 'HOUR';
			function phHistoryPoll(recall) {		
				$.ajax({
					dataType : "json",
					url : "/ph_history?interval=" + ph_interval,
					type : "GET",
					success : function(phData) {	
						var pdata = {
							labels : phData.labels,
							datasets : [
							{
								fillColor : "rgba(151,187,205,0.5)",
								strokeColor : "rgba(151,187,205,1)",
								pointColor : "rgba(151,187,205,1)",
								pointStrokeColor : "#fff",
								data : phData.values,
							}]
						};
						options = {
							animation : false
						}
						if (phHistoryChart != null) {
							phHistoryChart.destroy();
						}
						var ctx = $("#phHistory").get(0).getContext("2d");
						$("#phHistory").attr('width', $("#phHistory").parent().parent().width());
						phHistoryChart = new Chart(ctx).Line(pdata, options);
					},
					complete :  function() {
						if (recall) {
							setTimeout(function() {phHistoryPoll(true)}, 10000)
						}
					},
					timeout : 1000
				});
			}
			$(document).on("pageshow", "#ph_timeline_page", function(event) {
				phHistoryPoll(true);
				$("[name=phHistorySelection]").change(function() {
					ph_interval = $(this).val();
					phHistoryPoll(false);
				});
			});
			</script>
	</div>
	<!-- /page -->
</body>
</html>