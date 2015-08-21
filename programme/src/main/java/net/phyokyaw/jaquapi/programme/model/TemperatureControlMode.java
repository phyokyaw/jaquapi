package net.phyokyaw.jaquapi.programme.model;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.phyokyaw.jaquapi.core.model.Mode;
import net.phyokyaw.jaquapi.core.services.ScheduledExcutionService;
import net.phyokyaw.jaquapi.temperature.services.TemperatureService;

public class TemperatureControlMode extends Mode {
	private static final Logger logger = LoggerFactory
			.getLogger(Programme.class);

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
		boolean newValue = (onWhenLower) ? (currentTemp < lowerTemp)
				: (currentTemp > upperTemp);
		if (newValue != setValue && !changing) {
			setValue = newValue;
			changing = true;
			logger.debug("should be on to" + setValue + " with current temp "
					+ currentTemp + " onWhenLower is " + onWhenLower);
			schedule = scheduledExcutionService.addScheduleAtFixrate(1000 * 1,
					new Runnable() {
						@Override
						public void run() {
							double newTemp = temperatureService
									.getTemperature().getValue();
							boolean shouldContinue = (onWhenLower) ? (newTemp < upperTemp)
									: (newTemp > lowerTemp);
							if (!shouldContinue) {
								schedule.cancel(true);
								setValue = false;
								changing = false;
								logger.debug("should be on to" + setValue
										+ " with current temp " + newTemp
										+ " onWhenLower is " + onWhenLower);
							}
						}
					}, 1000 * 5);
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

	@Override
	public String getInfo() {
		return "Temperature Control";
	}
	
	@Override
	public String getFormattedInfo() {
		StringBuilder info = new StringBuilder();
		info.append("Temperature Control: ");
		info.append("<b>ON</b> when the temperature is ");
		if (onWhenLower) {
			info.append("lower than <b>" + lowerTemp + "&deg;C</b> and <b>OFF</b> when <b>" + upperTemp + "&deg;C</b> is reached.");
		} else {
			info.append("higher than <b>" + upperTemp + "&deg;C</b> and <b>OFF</b> when <b>" + lowerTemp + "&deg;C</b> is reached.");
		}
		 return info.toString();
	}
}
