package net.phyokyaw.jaquapi.dao.model;

import com.pi4j.io.gpio.GpioPinDigitalOutput;


public class WaveMaker extends I2CDevice {
	public WaveMaker(GpioPinDigitalOutput pin) {
		super(pin);
	}
}
