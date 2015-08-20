package net.phyokyaw.jaquapi.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService;
import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService.DeviceStatus;

@Controller
public class Home {


	@Autowired
	@Qualifier("programme")
	private PowerControlDeviceService powerControlDeviceService;

	@RequestMapping("/")
	public ModelAndView home(HttpServletRequest request)
	{
		Device device = DeviceUtils.getCurrentDevice(request);
		String page;
		if (device.isMobile() || device.isTablet()) {
			page = "index-mobile";
		} else {
			page = "feed";
		}
		ModelAndView mav = new ModelAndView(page);
		net.phyokyaw.jaquapi.core.model.Device devices[] = powerControlDeviceService.getDevices();
		DeviceStatus[] deviceStatus = new DeviceStatus[devices.length];
		for (int i=0; i < devices.length; i++) {
			deviceStatus[i] = new DeviceStatus(devices[i]);
		}
		mav.addObject("deviceStatus", deviceStatus);
		return mav;

	}

}
