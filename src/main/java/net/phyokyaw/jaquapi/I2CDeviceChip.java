package net.phyokyaw.jaquapi;

import java.io.IOException;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

public class I2CDeviceChip {

	private I2CDevice device;

	public I2CDeviceChip() {
		try {
			I2CBus bus = I2CFactory.getInstance(0);
			device = bus.getDevice(32);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public I2CDevice getDevice() {
		return device;
	}
}
