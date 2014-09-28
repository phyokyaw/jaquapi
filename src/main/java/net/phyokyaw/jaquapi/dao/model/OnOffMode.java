package net.phyokyaw.jaquapi.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class OnOffMode extends Mode {
	
	@Column
	private final boolean shouldBeOn;

	public OnOffMode(boolean shouldBeOn) {
		this.shouldBeOn = shouldBeOn;
	}

	@Override
	public boolean shouldBeOn() {
		return shouldBeOn;
	}

}
