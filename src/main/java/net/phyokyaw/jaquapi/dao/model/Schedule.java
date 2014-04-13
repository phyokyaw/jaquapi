package net.phyokyaw.jaquapi.dao.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Schedule {
	protected final List<Device> devices = new CopyOnWriteArrayList<Device>();

	protected final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);


	public void addDevice(Device device) {
		devices.add(device);
	}

	public abstract void deactivate();
	public abstract void activate();
}
