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
import net.phyokyaw.jaquapi.programme.model.Programme.ProgrammeDevice;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.DeviceStatus;

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
		for (int i = 0; i < devices.length; i++) {
			deviceStatus[i] = new DeviceStatus(devices[i]);
		}
		return deviceStatus;
	}

	@RequestMapping("/device_status/{id}")
	public @ResponseBody DeviceStatus getDeviceStatus(@PathVariable("id") long id) {
		Device devices[] = powerControlDeviceService.getDevices();
		for (int i = 0; i < devices.length; i++) {
			if (devices[i].getId() == id) {
				return new DeviceStatus(devices[i]);
			}
		}
		return null;
	}

	@RequestMapping("/device/{id}")
	public @ResponseBody ModelAndView getDeviceDetail(@PathVariable("id") long id) {
		ModelAndView mav = new ModelAndView("device_detail");
		mav.addObject("device", powerControlDeviceService.getDevice(id));
		return mav;
	}

	@RequestMapping("/secure/device_override/{id}/{on}/{duration}")
	public @ResponseBody boolean deviceOverride(@PathVariable("id") long id, @PathVariable("on") boolean on,
			@PathVariable("duration") long duration) {
		powerControlDeviceService.getDevice(id).setOverridingMode(new OnOffMode(on), duration * 1000 * 60);
		return true;
	}

	@RequestMapping("/secure/cancel_device_override/{id}")
	public @ResponseBody boolean cancelOverride(@PathVariable("id") long id) {
		powerControlDeviceService.getDevice(id).cancelOverridingMode();
		return true;
	}

	@RequestMapping("/programmes")
	public @ResponseBody ModelAndView getProgrammes() {
		ModelAndView mav = new ModelAndView("maintenance");
		mav.addObject("programmesStatus", powerControlDeviceService.getProgrammesStatus());
		return mav;
	}

	@RequestMapping("/programme_detail/{id}")
	public @ResponseBody ModelAndView getProgrammes(@PathVariable("id") long id) {
		ModelAndView mav = new ModelAndView("programme_detail");
		mav.addObject("programmeStatus", powerControlDeviceService.getProgrammeStatus(id));
		return mav;
	}

	@RequestMapping("/secure/activate_programme/{id}")
	public String activateProgramme(@PathVariable("id") long id) {
		powerControlDeviceService.activateProgramme(id);
		return "redirect:/programmes";
	}

	@RequestMapping("/programme_devices_status/{id}")
	public @ResponseBody DeviceStatus[] getProgrammeDeviceStatus(@PathVariable("id") long id) {
		ProgrammeDevice[] devices = powerControlDeviceService.getProgrammeStatus(id).getProgrammeDevices();
		DeviceStatus[] deviceStatus = new DeviceStatus[devices.length];
		for (int i = 0; i < devices.length; i++) {
			deviceStatus[i] = new DeviceStatus(devices[i].getDevice());
		}
		return deviceStatus;
	}

	// @RequestMapping("/secure/deactivate_programme/{id}")
	// public void deactivateProgramme(@PathVariable("id") long id) {
	// powerControlDeviceService.deactivateProgramme(id);
	// }
	//
	// @RequestMapping("/programme_status/{id}")
	// public @ResponseBody ProgrammeStatus programmeStatus(@PathVariable("id")
	// long id) {
	// Programme programme = powerControlDeviceService.getProgramme(id);
	// return new ProgrammeStatus(programme);
	// }
}