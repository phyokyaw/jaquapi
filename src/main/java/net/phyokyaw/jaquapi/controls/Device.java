package net.phyokyaw.jaquapi.controls;

import net.phyokyaw.jaquapi.services.DeviceControlService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Device {
	private static Logger logger = LoggerFactory.getLogger(DeviceControlService.class);

	private final String name;
	private DeviceControl currentdeviceControl;

	public Device(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setActive(boolean active) {
		logger.info("Device " + this.getName() + " is now " + active);
	}

	protected void setCurrentDeviceControl(DeviceControl currentdeviceControl) {
		this.currentdeviceControl = currentdeviceControl;
	}

	public DeviceControl getCurrentdeviceControl() {
		return currentdeviceControl;
	}
}
