package net.phyokyaw.jaquapi.sensor.services;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		//		try {
		//			Runtime r = Runtime.getRuntime();
		//			Process p;
		//			String line;
		//			p = r.exec(new String[]{"ssh", AquaService.DEVICE_SSH_ADDR, ""});
		//			BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
		//			StringBuilder builder = new StringBuilder();
		//			while ((line = is.readLine()) != null) {
		//				builder.append(line);
		//			}
		//			Pattern pattern = Pattern.compile(".*t=(\\d+)");
		//			Matcher matcher = pattern.matcher(builder.toString());
		//			if (matcher.find()) {
		//				value = Double.parseDouble(matcher.group(1)) / 1000;
		//			} else {
		//				throw new Exception("Unable to get Temperature data");
		//			}
		//			System.out.flush();
		//			p.waitFor(); // wait for process to complete
		//			logger.info("Updating temp value with: " + value);
		//		} catch (Exception e) {
		//			logger.error("Error executing temp reader", e);
		//		}
		for (SensorDevice sensorDevice : sensorDevices) {
			sensorDevice.setOn(true);
		}
	}

	public SensorDevice[] getSensorDevices() {
		return sensorDevices.toArray(new SensorDevice[]{});
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
