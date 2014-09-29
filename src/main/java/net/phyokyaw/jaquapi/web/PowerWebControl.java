package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.services.PowerControlDeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PowerWebControl {

	@Autowired
	@Qualifier("power")
	private PowerControlDeviceService powerControlDeviceService;

	@RequestMapping("/powerStatus")
	public @ResponseBody PowerControlDeviceService.PowerStatus[] getPowerStatus() {
		return powerControlDeviceService.getPowerStatus();
	}

	@RequestMapping("/activateProgramme")
	public void activateProgramme(String programmeName) {

	}

	@RequestMapping("/setPower")
	public void setDeviceStatus(String deviceId, boolean isOn) {
		//
	}
}
