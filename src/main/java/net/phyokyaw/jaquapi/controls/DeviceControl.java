package net.phyokyaw.jaquapi.controls;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DeviceControl {

	private static Logger logger = LoggerFactory.getLogger(PauseDeviceControl.class);
	protected final Device[] devices;

	protected ScheduledExecutorService scheduledExecutorService;

	public DeviceControl(Device[] devices) {
		this.devices = devices;
	}

	public void deactivate() throws InterruptedException {
		for (Device device : devices) {
			device.setCurrentDeviceControl(null);
		}
		scheduledExecutorService.shutdown();
		scheduledExecutorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		logger.info("Schedule deactivated");
	};

	public void activate() throws Exception {
		scheduledExecutorService = Executors.newScheduledThreadPool(5);
		logger.info("Deactivating previous control");
		scheduledExecutorService.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					for (Device device : devices) {
						if (device.getCurrentdeviceControl() != null) {
							device.getCurrentdeviceControl().deactivate();
						}
						device.setCurrentDeviceControl(DeviceControl.this);
					}
				}catch (InterruptedException e) {
					logger.error("Error deactivating previous control", e);
				}
			}
		}, 0L, TimeUnit.MILLISECONDS);
		logger.info("Schedule activating");
	};
}
