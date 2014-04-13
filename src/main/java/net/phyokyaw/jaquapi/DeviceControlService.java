package net.phyokyaw.jaquapi;

import net.phyokyaw.jaquapi.dao.model.Device;
import net.phyokyaw.jaquapi.dao.model.RandomSchedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("deviceControl")
public class DeviceControlService {

	private static Logger logger = LoggerFactory.getLogger(DeviceControlService.class);

	private final Device wm = new Device("Left WM");
	public void setup() {
		RandomSchedule test = new RandomSchedule();
		wm.setSchedule(test);
	}
}
