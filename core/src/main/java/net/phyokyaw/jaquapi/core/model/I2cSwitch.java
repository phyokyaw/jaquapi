package net.phyokyaw.jaquapi.core.model;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

public class I2cSwitch implements Operatable {

	private final GpioPinDigitalOutput pin;

	public I2cSwitch(GpioPinDigitalOutput pin) {
		this.pin = pin;
	}

	@Override
	public void setOn(boolean isOn) {
		if (isOn) {
			pin.setState(PinState.LOW);
		} else {
			pin.setState(PinState.HIGH);
		}
	}

	@Override
	public boolean isOn() {
		return pin.getState() == PinState.LOW;
	}

	@Override
	public String toString() {
		return pin.getName();
	}
}
