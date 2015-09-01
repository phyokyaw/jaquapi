package net.phyokyaw.jaquapi.programme.services;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.core.model.Device;
import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.programme.model.Programme;
import net.phyokyaw.jaquapi.programme.model.Programme.ProgrammeDevice;

@Service("programme")
public class PowerControlDeviceService {
	private static final Logger logger = LoggerFactory.getLogger(PowerControlDeviceService.class);
	public static final String PROGRAM_NAME_FEEDING = "Feeding";
	@Resource(name="devices")
	private List<Device> devices;

	@Resource(name="programmes")
	private List<Programme> programmes;

	@Autowired
	private ScheduledService scheduledService;

	private ScheduledFuture<?> deviceUpdate;

	private Programme activedProgramme;

	@PostConstruct
	public void setup() {
		logger.info("Start scheduling");
		deviceUpdate = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				for (Device device : devices) {
					device.update();
				}
			}
		}, 1000);
	}

	@PreDestroy
	public void shutdown() {
		deviceUpdate.cancel(true);
	}

	public Collection<Programme> getProgrammes() {
		return programmes;
	}

	public void activateProgramme(long id) {
		for (Programme programme : programmes) {
			if (programme.getId() == id) {
				if (activedProgramme != null) {
					activedProgramme.deactivate();
				}
				programme.activate();
				activedProgramme = programme;
			}
		}
	}

	public Device[] getDevices() {
		return devices.toArray(new Device[]{});
	}

	public Device getDevice(long id) {
		for (Device device : devices) {
			if (device.getId() == id) {
				return device;
			}
		}
		return null;
	}

	public static class DeviceStatus {
		private final boolean on;
		private final long id;
		private final String modeInfo;
		private final long overridingModeTimeout;
		private final String name;
		public DeviceStatus(Device device) {
			id = device.getId();
			modeInfo = device.getMode().getInfo();
			on = device.getActiveMode().shouldBeOn();
			overridingModeTimeout = device.isOverridingModeScheduleActive() ? device.getOverridingModeTimeOut() : 0;
			name = device.getName();
		}
		public boolean isOn() {
			return on;
		}
		public long getId() {
			return id;
		}
		public String getModeInfo() {
			return modeInfo;
		}
		public String getName() {
			return name;
		}
		public long getOverridingModeTimeout() {
			return overridingModeTimeout;
		}

		public String getOverridingModeTimeoutFormatted() {
			return convertTime(overridingModeTimeout);
		}

		public boolean isOverridden() {
			return overridingModeTimeout > 0;
		}

		private static String convertTime(long millis) {
			long second = (millis / 1000) % 60;
			long minute = (millis / (1000 * 60)) % 60;
			long hour = (millis / (1000 * 60 * 60)) % 24;

			return (hour > 0) ? String.format("%02dh:%02dm:%02ds", hour, minute, second) : String.format("%02dm:%02ds", minute, second);
		}
	}

	public static class ProgrammeStatus {
		private final boolean on;
		private final long id;
		private final String name;
		private final DeviceStatus[] deviceStatus;
		public ProgrammeStatus(Programme programme) {
			name = programme.getName();
			id = programme.getId();
			deviceStatus = new DeviceStatus[programme.getDevices().size()];
			boolean deviceOverride = false;
			for (int i = 0; i < programme.getDevices().size(); i++) {
				Device device = programme.getDevices().get(i).getDevice();
				deviceStatus[i] = new DeviceStatus(device);
				if (!deviceOverride && device.isOverridingModeScheduleActive()) {
					deviceOverride = true;
				}
			}
			on = deviceOverride;
		}
		public long getId() {
			return id;
		}
		public boolean isOn() {
			return on;
		}
		public String getName() {
			return name;
		}
		public DeviceStatus[] getDeviceStatus() {
			return deviceStatus;
		}
	}

	public Programme getProgramme(long id) {
		for (Programme programme : programmes) {
			if (programme.getId() == id) {
				return programme;
			}
		}
		return null;
	}



	public void deactivateProgramme(long id) {
		for (Programme programme : programmes) {
			if (programme.getId() == id) {
				for (ProgrammeDevice device : programme.getDevices()) {
					if (device.getDevice().isOverridingModeScheduleActive()) {
						device.getDevice().cancelOverridingMode();
					}
				}
			}
		}
	}
}
