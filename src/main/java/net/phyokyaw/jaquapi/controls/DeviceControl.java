package net.phyokyaw.jaquapi.controls;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DeviceControl {

	private static Logger logger = LoggerFactory.getLogger(PauseDeviceControl.class);
	protected final Device[] devices;

	protected final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

	public DeviceControl(Device[] devices) {
		this.devices = devices;
	}

	public void deactivate() throws InterruptedException {
		for (Device device : devices) {
			device.setCurrentDeviceControl(null);
		}
	};

	public void activate() throws Exception {
		logger.info("Deactivating previous control");
		for (Device device : devices) {
			if (device.getCurrentdeviceControl() != null) {
				device.getCurrentdeviceControl().deactivate();
			}
			device.setCurrentDeviceControl(this);
		}
	};
}
