package net.phyokyaw.jaquapi.remote;

public interface MessageListener {
	void messageArrived(String message);
	void sensorStateChanged(boolean connectionState);
}
