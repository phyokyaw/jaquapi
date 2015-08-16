package net.phyokyaw.jaquapi.programme.model;

import org.springframework.beans.factory.annotation.Autowired;

import net.phyokyaw.jaquapi.core.model.Operatable;
import net.phyokyaw.jaquapi.temperature.services.TemperatureService;

public class TemperatureControlFan implements Operatable {
	
	private double onTemp;
	private double offTemp;
	private Operatable powerSwitch;
	
	@Autowired
	private TemperatureService service;
	
	@Override
	public void setOn(boolean isOn) {
		powerSwitch.setOn(service.getTemperature().getValue() > onTemp);
	}

	@Override
	public boolean isOn() {
		return powerSwitch.isOn();
	}

	public double getOnTemp() {
		return onTemp;
	}

	public void setOnTemp(double onTemp) {
		this.onTemp = onTemp;
	}

	public double getOffTemp() {
		return offTemp;
	}

	public void setOffTemp(double offTemp) {
		this.offTemp = offTemp;
	}
	
	public void setPowerSwitch(Operatable powerSwitch) {
		this.powerSwitch = powerSwitch;
	}
	
	public Operatable getpowerSwitch() {
		return this.powerSwitch;
	}
}
