package net.phyokyaw.jaquapi.remote;

public interface MessageListener {
	void messageArrived(String topic, String message);
	void sensorDeviceConnection(boolean sensorsAvailable);
}
