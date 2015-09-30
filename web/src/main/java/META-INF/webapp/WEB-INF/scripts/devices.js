
var functions = [];

var interval_time = 2500;

var tempData;
var phData;
var temp_gauge;
var ph_gauge;
var dashboardControlsRefresh = function() {
	$.ajax({
		dataType : "json",
		url : "/controller_data",
		type : "GET",
		beforeSend: function() { showPageLoadingMsg(); }, //Show spinner
        complete: function() { hidePageLoadingMsg() }, //Hide spinner
		success : function(data) {
			temp_gauge.setValue(data["temperatureRecord"].value);
			ph_gauge.setValue(data["phRecord"].value);
			dashboard_sensors(data["sensors"]);
			dashboard_devices(data["deviceStatus"]);
		},
		timeout : interval_time
	});
}

function showPageLoadingMsg() {
	$.mobile.loading('show');
}

function hidePageLoadingMsg() {
	$.mobile.loading('hide');
}
function dashboard_sensors(data) {
	for(var k in data) {
		if (data[k].onError == true) {
			$("#sensor_" + data[k].id + "_switch_status").text(data[k].onErrorMessage);
			$("#sensor_" + data[k].id + "_switch_status").css('background-color',"red");
		} else {
			$("#sensor_" + data[k].id + "_switch_status").text("OK");
			$("#sensor_" + data[k].id + "_switch_status").css('background-color',"green");
		}
	}
}

function dashboard_devices(data) {
	for(var k in data) {
		if (data[k].overridden == true) {
			if (data[k].on == true) {
				$("#device_" + data[k].id + "_switch_status").text("MAUNAL ON (" + data[k].overridingModeTimeoutFormatted + ")");
				$("#device_" + data[k].id + "_switch_status").css('background-color',"blue");
			} else {
				$("#device_" + data[k].id + "_switch_status").text("MAUNAL OFF (" + data[k].overridingModeTimeoutFormatted + ")");
				$("#device_" + data[k].id + "_switch_status").css('background-color',"black");
			}
		} else {
			if (data[k].on == true) {
				$("#device_" + data[k].id + "_switch_status").text("ON (AUTO)");
				$("#device_" + data[k].id + "_switch_status").css('background-color',"blue");
			} else {
				$("#device_" + data[k].id + "_switch_status").text("OFF (AUTO)");
				$("#device_" + data[k].id + "_switch_status").css('background-color',"black");
			}
		}
	}
}

$(document).on("pagecreate", "#dashboard", function( event ) {
	temp_gauge = new Gauge({
		renderTo    : 'tempGuage',
		width       : 135,
		height      : 135,
		glow        : true,
		units       : "C&deg;",
		title       : "Temp",
		minValue    : 21,
		maxValue    : 30,
		majorTicks  : ['21','22','23','24','25','26','27','28','29', '30'],
		minorTicks  : 0.5,
		strokeTicks : false,
		highlights  : [
			{ from : 21, to : 24, color : '#ff33aa' },
			{ from : 24, to : 27, color : '#00cc00' },
			{ from : 27, to : 30, color : '#ff0000' }
		],
		colors      : {
			needle     : { start : '#f00', end : '#00f' }
		},
		animation : {
			delay : 25,
			duration: 500,
			fn : 'bounce'
		}
	});
	temp_gauge.draw();
	
	ph_gauge = new Gauge({
		renderTo    : 'phGuage',
		width       : 135,
		height      : 135,
		glow        : true,
		units       : "",
		title       : "pH",
		minValue    : 7,
		maxValue    : 9,
		majorTicks  : ['7','7.5','8','8.5','9'],
		minorTicks  : 0.25,
		strokeTicks : false,
		highlights  : [
			{ from : 7, to : 7.7, color : '#ff33aa' },
			{ from : 7.7, to : 8.4, color : '#00cc00' },
			{ from : 8.4, to : 9, color : '#ff0000' }
		],
		colors      : {
			needle     : { start : '#f00', end : '#00f' }
		}
	});

	ph_gauge.draw();
});

// Device

$(document).on("pagecreate", "#device_detail_page", function( event ) {
	var isOn = "false";
	$("#device_programme_on").click(function(event) {
		$("#submit_device_change").text("Turn On");
		isOn = "true";
	});
	$("#device_programme_off").click(function(event) {
		$("#submit_device_change").text("Turn Off");
		isOn = "false";
	});
	$("#device_timeout_checkbox").change(function() {
		if($(this).is(':checked')) {
			$("#device_timout_minute_slider").slider("disable");
		} else {
			$("#device_timout_minute_slider").slider("enable");
		}
	});
	$("#submit_device_change").click(function() {
		$.ajax({
			dataType : "json",
			beforeSend: function() { showPageLoadingMsg(); }, //Show spinner
            complete: function() { hidePageLoadingMsg() }, //Hide spinner
			url : "/secure/device_override/" + $("#device_detail_id").val() + "/" + isOn + "/" + $("#device_timout_minute_slider").val(),
			type : "GET",
			success : function(data) {
				//
			},
			timeout : interval_time
		});
	});
	$("#device_programme_auto").click(function(event) {
		$.ajax({
			dataType : "json",			
			beforeSend: function() { showPageLoadingMsg(); }, //Show spinner
            complete: function() { hidePageLoadingMsg() }, //Hide spinner
			url : "/secure/cancel_device_override/" + $("#device_detail_id").val(),
			type : "GET",
			success : function(data) {
				//
			},
			timeout : interval_time
		});
	});
});

var device_status_refresh = function() {
	$.ajax({
		dataType : "json",
		beforeSend: function() { showPageLoadingMsg(); }, //Show spinner
        complete: function() { hidePageLoadingMsg() }, //Hide spinner
		url : "/device_status/" + $("#device_detail_id").val(),
		type : "GET",
		success : function(data) {
			if (data.overridden == true) {
				if (data.on == true) {
					$("#device_status").text("MAUNAL ON (" + data.overridingModeTimeoutFormatted + ")");
				} else {
					$("#device_status").text("MAUNAL OFF (" + data.overridingModeTimeoutFormatted + ")");
				}
			} else {
				if (data.on == true) {
					$("#device_status").text("ON (AUTO)");
				} else {
					$("#device_status").text("OFF (AUTO)");
				}
			}
		},
		timeout : interval_time
	});
}

// Sensor
var  sensor_status_refresh = function() {
	$.ajax({
		dataType : "json",
		beforeSend: function() { showPageLoadingMsg(); }, //Show spinner
        complete: function() { hidePageLoadingMsg() }, //Hide spinner
		url : "/sensor_status/" + $("#sensor_detail_id").val(),
		type : "GET",
		success : function(data) {
			if (data.onError == true) {
				$("#sensor_detail_" + data.id + "_switch_status").text(data.onErrorMessage);
				$("#sensor_detail_" + data.id + "_switch_status").css('color',"red");
			} else {
				$("#sensor_detail_" + data.id + "_switch_status").text("OK");
				$("#sensor_detail_" + data.id + "_switch_status").css('color',"green");
			}
		},
		timeout : interval_time
	});
}

// ph timeline
var phHistoryChart;
var ph_interval = 'HOUR';

var  ph_timeline_refresh = function() {
	$.ajax({
		dataType : "json",
		beforeSend: function() { showPageLoadingMsg(); }, //Show spinner
        complete: function() { hidePageLoadingMsg() }, //Hide spinner
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
		timeout : interval_time
	});
}

$(document).on("pagecreate", "#ph_timeline_page", function( event ) {
	$("[name=phHistorySelection]").change(function() {
		ph_interval = $(this).val();
		ph_timeline_refresh();
	});
});

// temp timeline
var tempHistoryChart;
var temp_interval = 'HOUR';

var  temp_timeline_refresh = function() {
	$.ajax({
		dataType : "json",
		beforeSend: function() { showPageLoadingMsg(); }, //Show spinner
        complete: function() { hidePageLoadingMsg() }, //Hide spinner
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
		timeout : interval_time
	});
}

$(document).on("pagecreate", "#temp_timeline_page", function( event ) {
	$("[name=temperatureHistorySelection]").change(function() {
		temp_interval = $(this).val();
		temp_timeline_refresh();
	});
});



var programme_detail_refresh = function() {
	$.ajax({
		dataType : "json",
		url : "/programme_devices_status/" + $("#programme_detail_id").val(),
		type : "GET",
		success : function(data) {	
			for(var k in data) {
				if (data[k].overridden == true) {
					if (data[k].on == true) {
						$("#programme_device_" + data[k].id + "_switch_status").text("MAUNAL ON (" + data[k].overridingModeTimeoutFormatted + ")");
						$("#programme_device_" + data[k].id + "_switch_status").css('background-color',"blue");
					} else {
						$("#programme_device_" + data[k].id + "_switch_status").text("MAUNAL OFF (" + data[k].overridingModeTimeoutFormatted + ")");
						$("#programme_device_" + data[k].id + "_switch_status").css('background-color',"black");
					}
				} else {
					if (data[k].on == true) {
						$("#programme_device_" + data[k].id + "_switch_status").text("ON (AUTO)");
						$("#programme_device_" + data[k].id + "_switch_status").css('background-color',"blue");
					} else {
						$("#programme_device_" + data[k].id + "_switch_status").text("OFF (AUTO)");
						$("#programme_device_" + data[k].id + "_switch_status").css('background-color',"black");
					}
				}
			}
		},
		timeout : interval_time
	});
}

// Parameters

$(document).on("pagecreate", "#live_page", function( event ) {
	var client = new WebSocket('ws://phyokyaw.net:8084/');
	var canvas = document.getElementById('videoCanvas');
	var player = new jsmpeg(client, {
		canvas : canvas
	});
});

$(document).on("pagecontainershow",function( event, ui ) {
	if (ui.toPage.attr('id') == "dashboard") {
		functions.push(dashboardControlsRefresh);
	} else if (ui.toPage.attr('id') == "device_detail_page") {
		functions.push(device_status_refresh);
	} else if (ui.toPage.attr('id') == "sensor_detail_page") {
		functions.push(sensor_status_refresh);
	} else if (ui.toPage.attr('id') == "temp_timeline_page") {
		showPageLoadingMsg();
		functions.push(temp_timeline_refresh);
	} else if (ui.toPage.attr('id') == "ph_timeline_page") {
		showPageLoadingMsg();
		functions.push(ph_timeline_refresh);
	} else if (ui.toPage.attr('id') == "programme_detail_page") {
		functions.push(programme_detail_refresh);
	}
});

$(document).on("pagecontainerbeforehide", function( event, ui ) {
	if (ui.prevPage.attr('id') == "dashboard") {
		functions.pop(dashboardControlsRefresh);
	} else if (ui.prevPage.attr('id') == "device_detail_page") {
		functions.pop(device_status_refresh);
	} else if (ui.prevPage.attr('id') == "sensor_detail_page") {
		functions.pop(sensor_status_refresh);
	} else if (ui.prevPage.attr('id') == "temp_timeline_page") {
		functions.pop(temp_timeline_refresh);
	} else if (ui.prevPage.attr('id') == "ph_timeline_page") {
		functions.pop(ph_timeline_refresh);
	} else if (ui.prevPage.attr('id') == "programme_detail_page") {
		functions.pop(programme_detail_refresh);
	}
});

$(document).ready(function() {
	setInterval(function() {
		functions.forEach(function(entry) {
			entry();
		});
	}, interval_time);
});



$(document).ready(function() {
	if (navigator.userAgent.match(/Android/i)) {
		window.scrollTo(0, 0); // reset in case prev not scrolled   
		var nPageH = $(document).height();
		var nViewH = window.outerHeight;
		if (nViewH > nPageH) {
			nViewH -= 250;
			$('BODY').css('height', nViewH + 'px');
		}
		window.scrollTo(0, 1);
	}
}); 
