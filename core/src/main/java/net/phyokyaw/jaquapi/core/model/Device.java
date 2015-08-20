package net.phyokyaw.jaquapi.core.model;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.phyokyaw.jaquapi.core.services.ScheduledService;

public class Device extends AbstractModel {

	private static final Logger logger = LoggerFactory.getLogger(Device.class);

	public static final String OVERRIDING_MODE_TIMEOUT = "overridingModeTimeOut";

	private Mode mode = new OnOffMode(true);
	private Operatable operableDevice;
	private Mode overridingMode;
	private final int id;
	private String name;

	@Autowired
	private ScheduledService scheduledService;

	public Device(int id) {
		this.id = id;
	}

	public Mode getMode() {
		return mode;
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

	public boolean isOverridingModeScheduleActive() {
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
		if (operableDevice != null) {
			try {
				operableDevice.setOn(on);
			} catch (Exception e) {
				logger.error("Unable to set on off", e);
			}
		}
		logger.debug(name + " mode is " + on);
	}

	public Operatable getOperatable() {
		return operableDevice;
	}

	public void setOperatable(Operatable operableDevice) {
		this.operableDevice = operableDevice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
}