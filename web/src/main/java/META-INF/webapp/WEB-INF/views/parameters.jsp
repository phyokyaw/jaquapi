<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Aquarium controller</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/jquery.mobile-1.4.5.min.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/c/mobile.css' />" />
<script type="text/javascript"
	src="<c:url value='/s/jquery-1.11.0.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/s/jquery.mobile-1.4.5.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/gauge.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/Chart.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/s/devices.js' />"></script>
</head>
<body>
	<div data-role="page">
		<div data-role="header" style="overflow: hidden;">
			<h4>Aquarium control</h4>
			<a href="/secure/setup" data-icon="gear"
				class="ui-btn-right ui-shadow ui-corner-all ui-btn-icon-notext">Setup</a>
			<div data-role="navbar">
				<ul>
					<li><a href="/" data-icon="home">Dushboard</a></li>
					<li><a href="/programmes" data-icon="action">Maintenance</a></li>
					<li><a href="/parameters" data-icon="star"
						class="ui-btn-active ui-state-persist">Params</a></li>
				</ul>
			</div>
			<!-- /navbar -->
		</div>
		<!-- /header -->
		<div role="main" class="ui-content">
			<h4 class="ui-bar ui-bar-a" align="center">Parameters</h4>
			<ul data-role="listview" data-inset="true">
				<c:forEach items="${parameters}" var="element">
					<li><a href="/parameter_records/${element.id}">
							<h3>${element.name} (${element.shortName})</h3> 
							<c:choose>
								<c:when test="${element.lastRecord == null}">
									<p>No records found</p>
								</c:when>
								<c:otherwise>
									<p>Last recorded ${element.lastRecord.timeFromNow()}</p>
									<span id="parameter_${element.id}_value" class="ui-li-count">${element.lastRecord.value}
										${element.unit}</span>
								</c:otherwise>
							</c:choose>
					</a></li>
				</c:forEach>
			</ul>
		</div>
		<!-- /content -->
	</div>
	<!-- /page -->

</body>
</html>