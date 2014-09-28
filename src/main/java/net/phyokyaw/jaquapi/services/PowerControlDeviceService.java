package net.phyokyaw.jaquapi.services;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.phyokyaw.jaquapi.dao.model.Device;
import net.phyokyaw.jaquapi.dao.model.Programme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("power")
public class PowerControlDeviceService {
	private static Logger logger = LoggerFactory.getLogger(PowerControlDeviceService.class);

	@Autowired
	private List<Device> devices;

	@Autowired
	private ScheduledService scheduledService;

	private ScheduledFuture<?> deviceUpdate;

	//@Autowired
	private List<Programme> programmes;

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

	public void activateProgramme(Programme programme) {
		if (activedProgramme != null) {
			activedProgramme.deactivate();
		}
		activedProgramme = programme;
		activedProgramme.activate();
	}
}
