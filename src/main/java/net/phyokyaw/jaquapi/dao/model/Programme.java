package net.phyokyaw.jaquapi.dao.model;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Programme {
	private static final Logger logger = LoggerFactory.getLogger(Programme.class);
	
	private String name;
	private long timeout;

	@Autowired
	private Map<Device, Boolean> devices;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	public void activate() {
		logger.debug(name + " programme activated");
		for (Map.Entry<Device, Boolean> entry : devices.entrySet()) {
			entry.getKey().setOverridingMode(new OnOffMode(entry.getValue()), timeout);
		}
	}
	
	public void deactivate() {
		logger.debug(name + " programme deactivated");
		for (Device entry : devices.keySet()) {
			entry.cancelOverridingMode();
		}
	}
}
