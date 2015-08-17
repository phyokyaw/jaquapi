package net.phyokyaw.jaquapi.programme.model;

import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import net.phyokyaw.jaquapi.core.model.Mode;
import net.phyokyaw.jaquapi.core.model.Operatable;
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
		final double currentTemp = temperatureService.getTemperature().getValue();
		if (!changing) {
			setValue = (onWhenLower) ? (currentTemp < lowerTemp) : (currentTemp > upperTemp);
			changing = true;
			schedule = scheduledExcutionService.addScheduleAtFixrate(1000 * 1, new Runnable() {
				@Override
				public void run() {
					boolean shouldContinue = (onWhenLower) ? (currentTemp < upperTemp) : (currentTemp > lowerTemp);
					if (!shouldContinue) {
						schedule.cancel(true);
						changing = false;
					}
				}
			}, 1000 * 5);
			return setValue;
		} else {
			return setValue;
		}
	}
}
