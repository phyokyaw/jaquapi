package net.phyokyaw.jaquapi.programme.model;

import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;

import net.phyokyaw.jaquapi.core.model.Mode;
import net.phyokyaw.jaquapi.core.services.ScheduledExcutionService;
import net.phyokyaw.jaquapi.temperature.services.TemperatureService;

public class TemperatureControlMode extends Mode {

	private double upperTemp;
	private double lowerTemp;
	private boolean onWhenLower;

	private boolean setValue;
	private volatile boolean changing = false;

	@Autowired
	private TemperatureService temperatureService;

	@Autowired
	private ScheduledExcutionService scheduledExcutionService;
	private ScheduledFuture<?> schedule;

	@Override
	public boolean shouldBeOn() {
		double currentTemp = temperatureService.getTemperature().getValue();
		boolean newValue = (onWhenLower) ? (currentTemp < lowerTemp) : (currentTemp > upperTemp);
		if (newValue != setValue) {
			if (!changing) {
				setValue = newValue;
				changing = true;
				System.out.println("start run");
				schedule = scheduledExcutionService.addScheduleAtFixrate(1000 * 1, new Runnable() {
					@Override
					public void run() {
						double newTemp = temperatureService.getTemperature().getValue();
						boolean shouldContinue = (onWhenLower) ? (newTemp < upperTemp) : (newTemp > lowerTemp);
						if (!shouldContinue) {
							schedule.cancel(true);
							setValue = false;
							changing = false;
						}
					}
				}, 1000 * 5);
			}
		}
		return setValue;
	}

	public double getUpperTemp() {
		return upperTemp;
	}

	public void setUpperTemp(double upperTemp) {
		this.upperTemp = upperTemp;
	}

	public double getLowerTemp() {
		return lowerTemp;
	}

	public void setLowerTemp(double lowerTemp) {
		this.lowerTemp = lowerTemp;
	}

	public boolean isOnWhenLower() {
		return onWhenLower;
	}

	public void setOnWhenLower(boolean onWhenLower) {
		this.onWhenLower = onWhenLower;
	}
}
