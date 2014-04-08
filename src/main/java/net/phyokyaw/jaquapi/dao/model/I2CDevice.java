package net.phyokyaw.jaquapi.dao.model;

import net.phyokyaw.jaquapi.I2CDeviceChip;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class I2CDevice {
	public static final String I2C_ID = "I2C";

	@Autowired
	private I2CDeviceChip chip;

	private final int i2Cpin;

	public I2CDevice(int i2Cpin) {
		this.i2Cpin = i2Cpin;
	}
	public int getI2Cpin() {
		return i2Cpin;
	}

	public void setState(boolean isOn) {

	}
}
