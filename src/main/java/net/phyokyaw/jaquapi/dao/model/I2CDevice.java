package net.phyokyaw.jaquapi.dao.model;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

public abstract class I2CDevice {

	protected GpioPinDigitalOutput pin;

	public I2CDevice(GpioPinDigitalOutput pin) {
		this.pin = pin;
	}

	public void setState(boolean isOn) {
		//
	}
}
