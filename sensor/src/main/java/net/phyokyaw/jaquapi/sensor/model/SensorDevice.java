package net.phyokyaw.jaquapi.sensor.model;

import java.util.concurrent.ScheduledFuture;

import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.remote.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SensorDevice implements MessageListener {
	private static Logger logger = LoggerFactory.getLogger(SensorDevice.class);
	private final long id;
	private boolean on;
	private String name;
	private String description;
	private boolean errorWhenOn;
	private String onErrorMessage;
	
	@Autowired
	private ScheduledService scheduledService;
	private ScheduledFuture<?> recordSchedule;

	public SensorDevice(long id) {
		this.id = id;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public boolean isErrorWhenOn() {
		return errorWhenOn;
	}

	public void setErrorWhenOn(boolean errorWhenOn) {
		this.errorWhenOn = errorWhenOn;
	}

	public String getOnErrorMessage() {
		return onErrorMessage;
	}

	public void setOnErrorMessage(String onErrorMessage) {
		this.onErrorMessage = onErrorMessage;
	}

	public boolean isOnError() {
		return (on == errorWhenOn);
	}

	@Override
	public void messageArrived(String topic, String message) {
		setOn(Integer.parseInt(message) == 1);
		if (isOnError()) {
			recordSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
				@Override
				public void run() {
					logger.error("Error on " + name);
				}
			}, 1000 * 10);
		} else {
			if (recordSchedule != null) {
				recordSchedule.cancel(false);
			}
		}
	}

	@Override
	public void sensorDeviceConnection(boolean sensorDeviceConnected) {}

}
