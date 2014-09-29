package net.phyokyaw.jaquapi.dao.model;

import net.phyokyaw.jaquapi.services.ScheduledService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Device {
	private I2CDevice i2cDevice;
	private static final Logger logger = LoggerFactory.getLogger(Device.class);
	private Mode mode = new OnOffMode(true);
	public Mode getMode() {
		return mode;
	}

	private Mode overridingMode;
	private final String name;

	@Autowired
	private ScheduledService scheduledService;

	public Device(String name) {
		this.name = name;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public void setOverridingMode(Mode mode, long timeOutInMilliSec) {
		overridingMode = mode;
		scheduledService.addSchedule(timeOutInMilliSec, new Runnable() {
			@Override
			public void run() {
				logger.info("Canceling");
				cancelOverridingMode();
			}
		});
	}

	public void cancelOverridingMode() {
		overridingMode = null;
	}

	public void update() {
		boolean shouldBeOn = overridingMode != null ? overridingMode.shouldBeOn() : mode.shouldBeOn();
		setOn(shouldBeOn);
	}

	private void setOn(boolean on) {
		logger.info(name + " mode is " + on);
		if (i2cDevice != null) {
			i2cDevice.setOn(on);
		}
	}

	public String getName() {
		return name;
	}

	public I2CDevice getI2cDevice() {
		return i2cDevice;
	}

	public void setI2cDevice(I2CDevice i2cDevice) {
		this.i2cDevice = i2cDevice;
	}
}