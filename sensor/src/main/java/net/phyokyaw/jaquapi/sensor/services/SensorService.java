package net.phyokyaw.jaquapi.sensor.services;

import java.util.List;

import javax.annotation.PostConstruct;

import net.phyokyaw.jaquapi.sensor.model.SensorDevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sensor")
public class SensorService {
	private static final Logger logger = LoggerFactory.getLogger(SensorService.class);


	@Autowired
	private List<SensorDevice> sensorDevices;


	@PostConstruct
	private void setup() {
		
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

//	@Override
//	public void setValue(String value) {
//		JSONObject obj = new JSONObject(value);
//		for (SensorDevice sensorDevice : sensorDevices) {
//			String keyId = Long.toString(sensorDevice.getId());
//			if (obj.has(keyId)) {
//				sensorDevice.setOn(obj.getInt(keyId) == 1);
//				if (sensorDevice.isOnError()) {
//					try {
//						SoundUtil.playClip();
//					} catch (IOException | UnsupportedAudioFileException | LineUnavailableException
//							| InterruptedException e) {
//						logger.error("Unable to play sound", e);
//					}
//				}
//			}
//		}
//	}
}
