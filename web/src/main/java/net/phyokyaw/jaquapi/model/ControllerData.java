package net.phyokyaw.jaquapi.model;

import net.phyokyaw.jaquapi.ph.model.PhRecord;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.DeviceStatus;
import net.phyokyaw.jaquapi.sensor.model.SensorDevice;
import net.phyokyaw.jaquapi.temperature.model.TemperatureRecord;

public class ControllerData {
	private DeviceStatus[] deviceStatus;
	private TemperatureRecord temperatureRecord;
	private PhRecord phRecord;
	private SensorDevice[] sensors;
	
	public DeviceStatus[] getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(DeviceStatus[] deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public TemperatureRecord getTemperatureRecord() {
		return temperatureRecord;
	}

	public void setTemperatureRecord(TemperatureRecord temperatureRecord) {
		this.temperatureRecord = temperatureRecord;
	}

	public PhRecord getPhRecord() {
		return phRecord;
	}

	public void setPhRecord(PhRecord phRecord) {
		this.phRecord = phRecord;
	}

	public SensorDevice[] getSensors() {
		return sensors;
	}

	public void setSensors(SensorDevice[] sensors) {
		this.sensors = sensors;
	}

}
