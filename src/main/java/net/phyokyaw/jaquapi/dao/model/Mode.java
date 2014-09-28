package net.phyokyaw.jaquapi.dao.model;

import javax.persistence.Entity;

@Entity
public abstract class Mode {
	public abstract boolean shouldBeOn();
}
