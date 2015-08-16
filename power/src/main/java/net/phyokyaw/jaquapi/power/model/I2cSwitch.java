package net.phyokyaw.jaquapi.power.model;


import net.phyokyaw.jaquapi.core.model.Operatable;

public class I2cSwitch implements Operatable {

	@Override
	public void setOn(boolean isOn) {
		if (isOn) {
			//
		} else {
			//
		}
	}

	@Override
	public boolean isOn() {
		return false;
	}

	@Override
	public String toString() {
		return "";
	}
}
