package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.services.PowerControlDeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Home {

	@Autowired
	@Qualifier("power")
	private PowerControlDeviceService powerControlDeviceService;

	@RequestMapping("/")
	public ModelAndView home()
	{
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("programmes", powerControlDeviceService.getProgrammes());
		mav.addObject("devices", powerControlDeviceService.getDevices());
		return mav;
	}

}
