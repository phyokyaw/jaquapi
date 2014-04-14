package net.phyokyaw.jaquapi.controls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PauseDeviceControl extends DeviceControl {
	private static Logger logger = LoggerFactory.getLogger(PauseDeviceControl.class);

	private final Map<Device, DeviceControl> currentControls = new HashMap<Device, DeviceControl>();
	private final boolean active;
	private final long timeout;

	private ScheduledFuture<?> pausedSchedule;

	public PauseDeviceControl(Device[] devices, boolean active, long timeout) {
		super(devices);
		this.active = active;
		this.timeout = timeout;
	}

	@Override
	public void activate() throws Exception {
		for (Device device : devices) {
			DeviceControl current = device.getCurrentdeviceControl();
			if (current != null) {
				currentControls.put(device, current);
			}
		}
		super.activate();
		for (Device device : devices) {
			device.setActive(active);
		}
		logger.info("paused active");
		scheduledExecutorService.schedule(new Runnable() {
			@Override
			public void run() {
				for(DeviceControl previousDeviceControl: currentControls.values()) {
					try {
						previousDeviceControl.activate();
					} catch (Exception e) {
						logger.error("Unable to resume previous control", e);
					}
				}
			}
		}, timeout, TimeUnit.MILLISECONDS);
	}
}