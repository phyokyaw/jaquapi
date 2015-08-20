<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Jaqua-pi aquarium controller</title>
<link rel="stylesheet"
	href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script
	src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
	<div data-role="page">
		<div data-role="header">...</div>
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
		<div data-role="footer">...</div>
	</div>
</body>
</html>