package net.phyokyaw.jaquapi.web;

import org.jboss.logging.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.phyokyaw.jaquapi.core.model.Device;
import net.phyokyaw.jaquapi.programme.model.Programme;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.DeviceStatus;

@Controller
public class PowerWebControl {
	private static Logger logger = LoggerFactory.getLogger(PowerWebControl.class);
	
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
	
	@RequestMapping("/feedinfo")
	public @ResponseBody ModelAndView feedInfo() {
		ModelAndView mav = new ModelAndView("feed");
		for (Programme p : powerControlDeviceService.getProgrammes()) {
			if (p.getName().equals("Feeding")) {
				mav.addObject("programme", p);
			}
		}
		return mav;
	}
	
	@RequestMapping("/programme/{name}")
	public @ResponseBody boolean programme(@PathVariable("name") String name) {
		try {
			powerControlDeviceService.activateProgramme(name);
		} catch (Exception e) {
			logger.error("Unable to activate programme" + name, e);
			return false;
		}
		return true;
	}
}
