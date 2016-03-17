package net.phyokyaw.jaquapi.sensor.services;

import java.util.List;

import javax.annotation.PostConstruct;

import net.phyokyaw.jaquapi.remote.RemoteMessagingService;
import net.phyokyaw.jaquapi.sensor.model.SensorDevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sensor")
public class SensorService {
//	private static final Logger logger = LoggerFactory.getLogger(SensorService.class);
	private static final String TOPIC = "/fishtank/switch/";
	@Autowired
	private RemoteMessagingService messagingService;

	@Autowired
	private List<SensorDevice> sensorDevices;


	@PostConstruct
	private void setup() {
		for(SensorDevice sensorDevice : sensorDevices) {
			messagingService.addMessageListener(TOPIC + sensorDevice.getId(), sensorDevice);
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
