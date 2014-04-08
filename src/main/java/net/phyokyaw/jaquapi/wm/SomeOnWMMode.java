package net.phyokyaw.jaquapi.wm;

import net.phyokyaw.jaquapi.dao.model.WaveMaker;

public class SomeOnWMMode extends WaveMakerMode {
	private String[] name;
	private boolean isOn;
	public SomeOnWMMode(WaveMaker[] wm, String[] name, boolean isOn) {
		super(wm);
		// TODO Auto-generated constructor stub
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
