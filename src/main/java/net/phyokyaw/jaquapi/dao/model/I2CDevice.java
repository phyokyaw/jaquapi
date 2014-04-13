package net.phyokyaw.jaquapi.dao.model;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

public class I2CDevice {

	private final GpioPinDigitalOutput pin;

	public I2CDevice(GpioPinDigitalOutput pin) {
		this.pin = pin;
	}

	public void setOn(boolean isOn) {
		if (isOn) {
			pin.setState(PinState.LOW);
		} else {
			pin.setState(PinState.HIGH);
		}
	}

	public boolean isOn() {
		return pin.getState() == PinState.LOW;
	}

	@Override
	public String toString() {
		return pin.getName();
	}
}
