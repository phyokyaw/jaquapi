package net.phyokyaw.jaquapi.core.model;

public interface Operatable {
	void setOn(boolean isOn) throws Exception;
	boolean isOn() throws Exception;
	@Override
	String toString();
}
