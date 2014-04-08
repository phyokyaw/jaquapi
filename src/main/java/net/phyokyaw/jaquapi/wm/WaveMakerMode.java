package net.phyokyaw.jaquapi.wm;

import net.phyokyaw.jaquapi.ScheduledService;
import net.phyokyaw.jaquapi.dao.model.WaveMaker;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class WaveMakerMode {

	protected final WaveMaker[] wm;

	public WaveMakerMode(WaveMaker[] wm) {
		this.wm = wm;
	}

	@Autowired
	protected ScheduledService scheduledService;

	public abstract void deactivate();
	public abstract void activate();
}
