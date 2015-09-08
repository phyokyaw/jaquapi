package net.phyokyaw.jaquapi.core.services;

public interface AquaService {
	public static final String DEVICE_SSH_ADDR = "pi@192.168.0.123";
	public enum HistoryInterval{HOUR, DAY, WEEK, MONTH};
	double getValue();
}
