package net.phyokyaw.jaquapi.dao.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Mode {

	protected final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

	public abstract void deactivate();
	public abstract void activate();
}
