package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.core.model.Device;
import net.phyokyaw.jaquapi.core.model.OnOffMode;
import net.phyokyaw.jaquapi.programme.model.Programme;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.DeviceStatus;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.ProgrammeStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PowerWebControl {

	private static Logger logger = LoggerFactory.getLogger(PowerWebControl.class);
	
	@Autowired
	@Qualifier("programme")
	private PowerControlDeviceService powerControlDeviceService;

	@RequestMapping("/device_status")
	public @ResponseBody DeviceStatus[] getDeviceStatus() {
		Device devices[] = powerControlDeviceService.getDevices();
		DeviceStatus[] deviceStatus = new DeviceStatus[devices.length];
		for (int i=0; i < devices.length; i++) {
			deviceStatus[i] = new DeviceStatus(devices[i]);
		}
		return deviceStatus;
	}
	
	@RequestMapping("/device/{id}")
	public @ResponseBody ModelAndView getDeviceDetail(@PathVariable("id") long id) {
		ModelAndView mav = new ModelAndView("device_detail");
		mav.addObject(powerControlDeviceService.getDevice(id));
		return mav;
	}
	
	@RequestMapping("/device/{id}/{on}/{duration}")
	public @ResponseBody boolean deviceOverride(@PathVariable("id") long id, @PathVariable("on") boolean on, @PathVariable("id") long duration) {
		powerControlDeviceService.getDevice(id).setOverridingMode(new OnOffMode(on), duration * 1000);
		return true;
	}
	
	@RequestMapping("/feed_info")
	public @ResponseBody ModelAndView feedInfo() {
		ModelAndView mav = new ModelAndView("feed");
		for (Programme p : powerControlDeviceService.getProgrammes()) {
			if (p.getName().equals(PowerControlDeviceService.PROGRAM_NAME_FEEDING)) {
				mav.addObject("programme", p);
			}
		}
		return mav;
	}
	
	@RequestMapping("/activate_programme/{name}")
	public @ResponseBody boolean ActivateProgramme(@PathVariable("name") String name) {
		try {
			powerControlDeviceService.activateProgramme(name);
		} catch (Exception e) {
			logger.error("Unable to activate programme" + name, e);
			return false;
		}
		return true;
	}
	
	@RequestMapping("/deactivate_programme/{name}")
	public @ResponseBody boolean deactivateProgramme(@PathVariable("name") String name) {
		try {
			powerControlDeviceService.deactivateProgramme(name);
		} catch (Exception e) {
			logger.error("Unable to deactivate programme" + name, e);
			return false;
		}
		return true;
	}
	
	@RequestMapping("/programme_status/{name}")
	public @ResponseBody ProgrammeStatus programmeStatus(@PathVariable("name") String name) {
		Programme programme = powerControlDeviceService.getProgramme(name);
		return new ProgrammeStatus(programme);
	}
}
