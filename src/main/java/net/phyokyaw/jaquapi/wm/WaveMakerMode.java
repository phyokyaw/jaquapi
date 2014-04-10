package net.phyokyaw.jaquapi.wm;

import net.phyokyaw.jaquapi.dao.model.Mode;
import net.phyokyaw.jaquapi.dao.model.WaveMaker;

public abstract class WaveMakerMode extends Mode {

	protected final WaveMaker[] availableWMs;

	public WaveMakerMode(WaveMaker[] availableWMs) {
		this.availableWMs = availableWMs;
	}
}
