package net.phyokyaw.jaquapi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.phyokyaw.jaquapi.core.model.Device;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.DeviceStatus;

@Controller
public class PowerWebControl {
	@Autowired
	@Qualifier("programme")
	private PowerControlDeviceService powerControlDeviceService;

	@RequestMapping("/devicestatus")
	public @ResponseBody DeviceStatus[] getDeviceStatus() {
		Device devices[] = powerControlDeviceService.getDevices();
		DeviceStatus[] deviceStatus = new DeviceStatus[devices.length];
		for (int i=0; i < devices.length; i++) {
			deviceStatus[i] = new DeviceStatus(devices[i]);
		}
		return deviceStatus;
	}

}
