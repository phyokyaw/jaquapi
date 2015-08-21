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
		<div data-role="header" style="overflow: hidden;">
			<h4>Feed</h4>
			<a href="#" data-icon="gear"
				class="ui-btn-right ui-shadow ui-corner-all ui-btn-icon-notext">Setup</a>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home">Dushboard</a></li>
					<li><a href="#" data-icon="star" class="ui-btn-active ui-state-persist">Feed</a></li>
					<li><a href="/programmes" data-icon="action">Maintenance</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
		<ul data-role="listview" data-inset="true">
				<li data-role="list-divider">Stop equiments for</li>
				
				<li>
					<form class="ui-field-contain">
						<label for="slider-12">Fan</label> <input type="range"
							name="slider-12" id="slider-12" data-highlight="true" min="0"
							step="5" max="30" value="5" />
					</form>
				</li>
				<li>Test</li>
			</ul>
			<button class="ui-btn ui-btn-b">Stop to Feed</button>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>