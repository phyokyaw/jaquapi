package net.phyokyaw.jaquapi.programme.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.phyokyaw.jaquapi.core.model.Device;
import net.phyokyaw.jaquapi.core.model.OnOffMode;

public class Programme {
	private static final Logger logger = LoggerFactory.getLogger(Programme.class);

	private final int id;
	private String name;
	
	private List<ProgrammeDevice> programmeDevices;
	
	public Programme(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void activate() {
		logger.debug(name + " programme activated");
		for (ProgrammeDevice entry : programmeDevices) {
			entry.getDevice().setOverridingMode(new OnOffMode(entry.isShouldbeOff()), entry.getTimeout() * 1000 * 60);
		}
	}

	public void deactivate() {
		logger.debug(name + " programme deactivated");
		for (ProgrammeDevice entry : programmeDevices) {
			entry.getDevice().cancelOverridingMode();
		}
	}

	public List<ProgrammeDevice> getDevices() {
		return programmeDevices;
	}

	public void setDevices(List<ProgrammeDevice> programmeDevices) {
		this.programmeDevices = programmeDevices;
	}

	public int getId() {
		return id;
	}

	public static class ProgrammeDevice {
		private Device device;
		private long timeout;
		private boolean shouldbeOff;

		public Device getDevice() {
			return device;
		}
		public void setDevice(Device device) {
			this.device = device;
		}
		public long getTimeout() {
			return timeout;
		}
		public void setTimeout(long timeout) {
			this.timeout = timeout;
		}
		public boolean isShouldbeOff() {
			return shouldbeOff;
		}
		public void setShouldbeOff(boolean shouldbeOff) {
			this.shouldbeOff = shouldbeOff;
		}
	}


}
