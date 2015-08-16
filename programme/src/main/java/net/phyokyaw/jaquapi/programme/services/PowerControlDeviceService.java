package net.phyokyaw.jaquapi.programme.services;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import net.phyokyaw.jaquapi.core.model.Device;
import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.programme.model.Programme;
import net.phyokyaw.jaquapi.power.services.PowerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("programme")
public class PowerControlDeviceService {
	private static Logger logger = LoggerFactory.getLogger(PowerControlDeviceService.class);

	@Resource(name="devices")
	private Map<Long, Device> devices;
	
	@Autowired
	private PowerService powerService;

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
				for (Device device : devices.values()) {
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

	public void activateProgramme(String programmeName) {
		if (activedProgramme != null) {
			activedProgramme.deactivate();
		}
		activedProgramme = programmes.get(programmeName);
		activedProgramme.activate();
	}

	public Device[] getDevices() {
		return devices.values().toArray(new Device[]{});
	}

	public Device getDevice(long id) {
		return devices.get(id);
	}

	public static class PowerStatus {
		public String name;
		public boolean isOn;
	}

	public PowerStatus[] getPowerStatus() {
		// TODO Auto-generated method stub
		return null;
	}
}