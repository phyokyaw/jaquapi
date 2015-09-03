package net.phyokyaw.jaquapi.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.DeviceStatus;
import net.phyokyaw.jaquapi.sensor.services.SensorService;

@Controller
public class Home {


	@Autowired
	@Qualifier("programme")
	private PowerControlDeviceService powerControlDeviceService;

	@Autowired
	@Qualifier("sensor")
	private SensorService sensorService;

	@RequestMapping("/")
	public ModelAndView home(HttpServletRequest request)
	{
		Device browserDevice = DeviceUtils.getCurrentDevice(request);
		String page = "index-mobile";
		ModelAndView mav = new ModelAndView(page);
		net.phyokyaw.jaquapi.core.model.Device devices[] = powerControlDeviceService.getDevices();
		DeviceStatus[] deviceStatus = new DeviceStatus[devices.length];
		for (int i=0; i < devices.length; i++) {
			deviceStatus[i] = new DeviceStatus(devices[i]);
		}
		mav.addObject("deviceStatus", deviceStatus);
		mav.addObject("browserDevice", browserDevice);
		mav.addObject("sensorDevices", sensorService.getSensorDevices());
		return mav;
	}

	
	public @ResponseBody ControllerData getControllerData() {
		return null;
	}
}
