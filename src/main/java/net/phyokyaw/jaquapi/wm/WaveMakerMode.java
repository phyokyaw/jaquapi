package net.phyokyaw.jaquapi.wm;

import net.phyokyaw.jaquap.Mode;
import net.phyokyaw.jaquapi.dao.model.WaveMaker;

public abstract class WaveMakerMode extends Mode {

	protected final WaveMaker[] wm;

	public WaveMakerMode(WaveMaker[] wm) {
		this.wm = wm;
	}
}
