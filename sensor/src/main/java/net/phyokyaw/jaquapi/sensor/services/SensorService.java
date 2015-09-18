package net.phyokyaw.jaquapi.sensor.services;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.core.SoundUtil;
import net.phyokyaw.jaquapi.remote.ControllerDataService;
import net.phyokyaw.jaquapi.remote.ValueUpdateListener;
import net.phyokyaw.jaquapi.sensor.model.SensorDevice;

@Service("sensor")
public class SensorService implements ValueUpdateListener {
	private static final Logger logger = LoggerFactory.getLogger(SensorService.class);

	@Autowired
	private ControllerDataService controllerDataService;

	@Autowired
	private List<SensorDevice> sensorDevices;


	@PostConstruct
	private void setup() {
		controllerDataService.addValueUpdateListener("sensors", this);
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

	@Override
	public void setValue(String value) {
		JSONObject obj = new JSONObject(value);
		for (SensorDevice sensorDevice : sensorDevices) {
			String keyId = Long.toString(sensorDevice.getId());
			if (obj.has(keyId)) {
				sensorDevice.setOn(obj.getInt(keyId) == 1);
				if (sensorDevice.isOnError()) {
					try {
						SoundUtil.playClip();
					} catch (IOException | UnsupportedAudioFileException | LineUnavailableException
							| InterruptedException e) {
						logger.error("Unable to play sound", e);
					}
				}
			}
		}
	}
}
