package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.core.model.DateTimeScheduleMode;
import net.phyokyaw.jaquapi.core.model.Device;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PowerWebControl {
	@Autowired
	@Qualifier("programme")
	private PowerControlDeviceService powerControlDeviceService;

	@RequestMapping("/devicestatus/{id}")
	public @ResponseBody DeviceStatus getDeviceStatus(@PathVariable long deviceId) {
		Device device = powerControlDeviceService.getDevice(deviceId);
		return new DeviceStatus(device);
	}

	public static class DeviceStatus {
		private boolean isOn;
		public DeviceStatus(Device device) {
			this.isOn = device.getMode().shouldBeOn();
		}
		public boolean isOn() {
			return isOn;
		}
	}
}
