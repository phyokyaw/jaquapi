package net.phyokyaw.jaquapi.dao.model;


public class OnOffMode extends Mode {

	private final boolean shouldBeOn;

	public OnOffMode(boolean shouldBeOn) {
		this.shouldBeOn = shouldBeOn;
	}

	@Override
	public boolean shouldBeOn() {
		return shouldBeOn;
	}

}
