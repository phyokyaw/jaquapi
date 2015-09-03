package net.phyokyaw.jaquapi.model;

import net.phyokyaw.jaquapi.ph.model.PhRecord;
import net.phyokyaw.jaquapi.ph.services.PhService;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.DeviceStatus;
import net.phyokyaw.jaquapi.sensor.services.SensorService;
import net.phyokyaw.jaquapi.temperature.model.TemperatureRecord;
import net.phyokyaw.jaquapi.temperature.services.TemperatureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ControllerData {
	private DeviceStatus[] deviceStatus;
	private TemperatureRecord temperatureRecord;
	private PhRecord phRecord;
	
	
	@Autowired
	@Qualifier("programme")
	private PowerControlDeviceService powerControlDeviceService;
	
	@Autowired
	@Qualifier("temperature")
	private TemperatureService temperatureService;
	
	@Autowired
	@Qualifier("ph")
	private PhService phService;
	
	@Autowired
	@Qualifier("sensor")
	private SensorService sensorService;
	
	public static ControllerData create() {
		return null;
	}

}
