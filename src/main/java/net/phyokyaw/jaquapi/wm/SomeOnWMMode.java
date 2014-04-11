package net.phyokyaw.jaquapi.wm;

import net.phyokyaw.jaquapi.dao.model.WaveMaker;

public class SomeOnWMMode extends WaveMakerMode {
	private final String[] names;
	public SomeOnWMMode(WaveMaker[] availableWMs, String[] names) {
		super(availableWMs);
		this.names = names;
	}

	@Override
	public void deactivate() {}

	@Override
	public void activate() {
		for (WaveMaker wm : availableWMs) {
			if (isInNames(wm.getName())) {
				wm.on();
			} else {
				wm.off();
			}
		}
	}

	private boolean isInNames(String name) {
		for (String entry : names) {
			if (entry.equals(name)) {
				return true;
			}
		}
		return false;
	}

}
