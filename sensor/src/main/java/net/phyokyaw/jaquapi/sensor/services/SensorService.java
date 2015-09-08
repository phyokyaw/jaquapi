package net.phyokyaw.jaquapi.sensor.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.core.SoundUtil;
import net.phyokyaw.jaquapi.core.services.AquaService;
import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.sensor.model.SensorDevice;

@Service("sensor")
public class SensorService {
	private static final Logger logger = LoggerFactory.getLogger(SensorService.class);

	@Autowired
	private ScheduledService scheduledService;

	@Autowired
	private List<SensorDevice> sensorDevices;

	private ScheduledFuture<?> updateSchedule;

	@PreDestroy
	private void shutdown() {
		if (updateSchedule != null) {
			updateSchedule.cancel(false);
		}
	}

	@PostConstruct
	private void setup() {
		updateSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				update();
			}
		}, 1000 * 20);
	}

	private void update() {
		logger.debug("Updating sensor value");
		try {
			Runtime r = Runtime.getRuntime();
			Process p;
			for (SensorDevice sensorDevice : sensorDevices) {
				p = r.exec(new String[] { "ssh", AquaService.DEVICE_SSH_ADDR, "gpio", "read",
						Long.toString(sensorDevice.getId()) });
				BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				if ((line = is.readLine()) != null) {
					if (line.equals("1")) {
						sensorDevice.setOn(true);
					} else if (line.equals("0")) {
						sensorDevice.setOn(false);
					} else {
						throw new Exception("Unable to get sensor data for " + sensorDevice.getName());
					}
					if (sensorDevice.isOnError()) {
						SoundUtil.playClip();
					}
				}
				System.out.flush();
				p.waitFor(); // wait for process to complete
			}
		} catch (Exception e) {
			logger.error("Error executing sensor reader", e);
		}
	}

	public SensorDevice[] getSensorDevices() {
		return sensorDevices.toArray(new SensorDevice[] {});
	}

	public SensorDevice getSensorDevice(long id) {
		for (SensorDevice sensorDevice : sensorDevices) {
			if (sensorDevice.getId() == id) {
				return sensorDevice;
			}
		}
		return null;
	}
}
