package net.phyokyaw.jaquapi.controls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultDeviceControl extends DeviceControl {
	private static Logger logger = LoggerFactory.getLogger(DefaultDeviceControl.class);
	private final boolean active;


	public DefaultDeviceControl(Device[] devices, boolean active) {
		super(devices);
		this.active = active;
	}

	@Override
	public void deactivate() {
		super.deactivate();
	}

	@Override
	public void activate() throws Exception {
		super.activate();
		for (int i = 0; i < devices.length; i++) {
			devices[i].setActive(active);
		}
	}

}
