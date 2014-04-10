package net.phyokyaw.jaquap;

import net.phyokyaw.jaquapi.ScheduledService;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class Mode {

	@Autowired
	protected ScheduledService scheduledService;

	public abstract void deactivate();
	public abstract void activate();
}
