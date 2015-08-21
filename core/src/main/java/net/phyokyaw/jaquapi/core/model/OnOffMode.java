package net.phyokyaw.jaquapi.core.model;


public class OnOffMode extends Mode {

	private final boolean shouldBeOn;

	public OnOffMode(boolean shouldBeOn) {
		this.shouldBeOn = shouldBeOn;
	}

	@Override
	public boolean shouldBeOn() {
		return shouldBeOn;
	}

	@Override
	public String getInfo() {
		return "";
	}

	@Override
	public String getFormattedInfo() {
		return "";
	}

}
