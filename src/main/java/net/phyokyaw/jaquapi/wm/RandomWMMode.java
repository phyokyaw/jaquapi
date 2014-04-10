package net.phyokyaw.jaquapi.wm;

import net.phyokyaw.jaquapi.dao.model.WaveMaker;

public class RandomWMMode extends WaveMakerMode {
	private final double start;
	private final double end;
	private final boolean isSync;

	public RandomWMMode(WaveMaker[] wm, double start, double end, boolean isSync) {
		super(wm);
		this.start = start;
		this.end= end;
		this.isSync = isSync;
	}

	@Override
	public void deactivate() {
		//
	}

	@Override
	public void activate() {
		//
	}

}
