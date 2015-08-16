package net.phyokyaw.jaquapi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.phyokyaw.jaquapi.programme.services.PowerControlDeviceService;

@Controller
public class Home {

	@Autowired
	@Qualifier("programme")
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