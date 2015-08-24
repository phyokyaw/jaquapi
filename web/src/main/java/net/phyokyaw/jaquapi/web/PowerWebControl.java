package net.phyokyaw.jaquapi.web;

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
import net.phyokyaw.jaquapi.core.model.OnOffMode;
import net.phyokyaw.jaquapi.programme.model.Programme;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.DeviceStatus;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.ProgrammeStatus;

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
		mav.addObject("device", powerControlDeviceService.getDevice(id));
		return mav;
	}

	@RequestMapping("/device_override/{id}/{on}/{duration}")
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

	@RequestMapping("/programmes")
	public @ResponseBody ModelAndView getProgrammes() {
		ModelAndView mav = new ModelAndView("maintenance");
		mav.addObject("programmes", powerControlDeviceService.getProgrammes());
		return mav;
	}

	@RequestMapping("/activate_programme/{id}")
	public void activateProgramme(@PathVariable("id") long id) {
		powerControlDeviceService.activateProgramme(id);
	}

	@RequestMapping("/deactivate_programme/{id}")
	public void deactivateProgramme(@PathVariable("id") long id) {
		powerControlDeviceService.deactivateProgramme(id);
	}

	@RequestMapping("/programme_status/{id}")
	public @ResponseBody ProgrammeStatus programmeStatus(@PathVariable("id") long id) {
		Programme programme = powerControlDeviceService.getProgramme(id);
		return new ProgrammeStatus(programme);
	}
}
