package net.phyokyaw.jaquapi.programme.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import net.phyokyaw.jaquapi.core.model.Device;
import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.programme.model.Programme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("programme")
public class PowerControlDeviceService {
	private static Logger logger = LoggerFactory.getLogger(PowerControlDeviceService.class);
	public static final String PROGRAM_NAME_FEEDING = "Feeding";
	@Resource(name="devices")
	private List<Device> devices;

	@Resource(name="programmes")
	private Map<Long, Programme> programmes;

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
		return programmes.values();
	}

	public void activateProgramme(String programmeName) throws Exception {
		Programme newActivedProgramme = programmes.get(programmeName);
		if (newActivedProgramme == null) {
			throw new Exception("Unable to find programme");
		}
		if (activedProgramme != null) {
			activedProgramme.deactivate();
		}
		newActivedProgramme.activate();
		activedProgramme = newActivedProgramme;
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

	public static class PowerStatus {
		public String name;
		public boolean isOn;
	}


	public static class DeviceStatus {
		private final boolean on;
		private final long id;
		private final String modeInfo;
		private final long overridingModeTimeout;
		private final String name;
		public DeviceStatus(Device device) {
			on = device.getMode().shouldBeOn();
			id = device.getId();
			modeInfo = device.getMode().getInfo();
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
	}
	
	public static class ProgrammeStatus {
		private  boolean on;
		private  String name;
		private DeviceStatus[] deviceStatus;
		public ProgrammeStatus(Programme programme) {
			
		}
		public boolean isOn() {
			return on;
		}
		public String getName() {
			return name;
		}
		
	}

	public Programme getProgramme(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deactivateProgramme(String name) {
		// TODO Auto-generated method stub
		
	}
}
