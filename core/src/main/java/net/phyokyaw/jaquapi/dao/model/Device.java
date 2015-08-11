package net.phyokyaw.jaquapi.dao.model;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.phyokyaw.jaquapi.services.ScheduledService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Device extends AbstractModel {
	private I2CDevice i2cDevice;
	private static final Logger logger = LoggerFactory.getLogger(Device.class);

	public static final String OVERRIDING_MODE_TIMEOUT = "overridingModeTimeOut";

	private Mode mode = new OnOffMode(true);
	public Mode getMode() {
		return mode;
	}

	private Mode overridingMode;
	private String name;
	private final int id;

	@Autowired
	private ScheduledService scheduledService;

	public Device(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	private ScheduledFuture<?> overridingModeSchedule;
	private ScheduledFuture<?> timeoutPublishSchedule;
	private final long interval = 100L;

	public void setOverridingMode(Mode mode, long timeOutInMilliSec) {
		overridingMode = mode;
		timeoutPublishSchedule = scheduledService.addScheduleAtFixrate(interval, new Runnable() {
			@Override
			public void run() {
				if (isOverridingModeScheduleActive()) {
					Device.this.firePropertyChange(OVERRIDING_MODE_TIMEOUT, null, overridingModeSchedule.getDelay(TimeUnit.MILLISECONDS));
				}
			}
		}, interval);
		createNewSchedule(timeOutInMilliSec);
	}

	private void createNewSchedule(long timeOutInMilliSec) {
		overridingModeSchedule = scheduledService.addSchedule(timeOutInMilliSec, new Runnable() {
			@Override
			public void run() {
				logger.info("Canceling");
				cancelOverridingMode();
				timeoutPublishSchedule.cancel(false);
			}
		});
	}

	private boolean isOverridingModeScheduleActive() {
		return (overridingModeSchedule != null && !overridingModeSchedule.isCancelled() && !overridingModeSchedule.isDone());
	}

	public void updateOverridingMode(long timeOutInMilliSec) {
		synchronized (this) {
			if (overridingMode == null) {
				return;
			}
			long remainingTime = 0L;
			if (isOverridingModeScheduleActive()) {
				remainingTime = overridingModeSchedule.getDelay(TimeUnit.MILLISECONDS);
				overridingModeSchedule.cancel(true);
				setOverridingMode(overridingMode, remainingTime + timeOutInMilliSec);
			}
			createNewSchedule(remainingTime + timeOutInMilliSec);
		}
	}

	public void cancelOverridingMode() {
		synchronized (this) {
			overridingMode = null;
		}
	}

	public long getOverridingModeTimeOut() {
		return overridingModeSchedule.getDelay(TimeUnit.MILLISECONDS);
	}

	public void update() {
		boolean shouldBeOn = overridingMode != null ? overridingMode.shouldBeOn() : mode.shouldBeOn();
		setOn(shouldBeOn);
	}

	private void setOn(boolean on) {
		logger.info(name + " mode is " + on);
		if (i2cDevice != null) {
			i2cDevice.setOn(on);
		}
	}

	public String getName() {
		return name;
	}

	public I2CDevice getI2cDevice() {
		return i2cDevice;
	}

	public void setI2cDevice(I2CDevice i2cDevice) {
		this.i2cDevice = i2cDevice;
	}

	public int getId() {
		return id;
	}
}