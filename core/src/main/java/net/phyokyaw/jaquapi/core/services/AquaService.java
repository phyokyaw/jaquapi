package net.phyokyaw.jaquapi.core.services;

public interface AquaService {
	public enum HistoryInterval{HOUR, DAY, WEEK, MONTH};
	double getValue();
}
