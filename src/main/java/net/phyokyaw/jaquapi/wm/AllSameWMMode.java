package net.phyokyaw.jaquapi.wm;

import net.phyokyaw.jaquapi.dao.model.WaveMaker;

public class AllSameWMMode extends WaveMakerMode {
	private final boolean isOn;
	public AllSameWMMode(WaveMaker[] wm, boolean isOn) {
		super(wm);
		this.isOn = isOn;
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
	}
}
