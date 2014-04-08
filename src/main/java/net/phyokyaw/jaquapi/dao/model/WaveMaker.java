package net.phyokyaw.jaquapi.dao.model;


public class WaveMaker extends I2CDevice {
	public WaveMaker(int i2Cpin) {
		super(i2Cpin);
	}

	public String getID() {
		return I2C_ID + this.getI2Cpin();
	}
}
