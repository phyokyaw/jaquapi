package net.phyokyaw.jaquapi.sensor.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.remote.ControllerDataService;
import net.phyokyaw.jaquapi.remote.ValueUpdateListener;
import net.phyokyaw.jaquapi.sensor.model.SensorDevice;

@Service("sensor")
public class SensorService implements ValueUpdateListener {

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
			}
		}
	}
}
