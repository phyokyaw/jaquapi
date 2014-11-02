package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.dao.model.DateTimeScheduleMode;
import net.phyokyaw.jaquapi.dao.model.Device;
import net.phyokyaw.jaquapi.services.PowerControlDeviceService;

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

	@RequestMapping("/createDateTimeSchedulePage/device/{id}")
	public ModelAndView createDateTimeSchedulePage(@PathVariable long deviceId) {
		ModelAndView mav = new ModelAndView("createsdatetimechedule");
		mav.addObject("deviceId", deviceId);
		return mav;
	}

	@RequestMapping("/createDateTimeSchedule/device/{id}")
	public Device createDateTimeSchedulePage(@PathVariable long id, @RequestBody DateTimeScheduleMode dateTimeScheduleMode) {
		Device device = powerControlDeviceService.getDevice(id);
		device.setMode(dateTimeScheduleMode);
		return device;
	}

	@RequestMapping("/devices")
	public @ResponseBody Device[] getDevices() {
		return powerControlDeviceService.getDevices();
	}
}
