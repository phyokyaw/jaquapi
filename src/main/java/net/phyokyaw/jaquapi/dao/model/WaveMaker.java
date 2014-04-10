package net.phyokyaw.jaquapi.dao.model;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class WaveMaker extends I2CDevice {
	private final String name;

	public WaveMaker(String name, GpioPinDigitalOutput pin) {
		super(pin);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
