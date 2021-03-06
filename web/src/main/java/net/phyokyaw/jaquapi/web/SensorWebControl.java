package net.phyokyaw.jaquapi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.phyokyaw.jaquapi.sensor.model.SensorDevice;
import net.phyokyaw.jaquapi.sensor.services.SensorService;

@Controller
public class SensorWebControl {
	@Autowired
	@Qualifier("sensor")
	private SensorService sensorService;

	@RequestMapping("/sensor_detail/{id}")
	public ModelAndView getSensorsDetail(@PathVariable("id") long id) {
		ModelAndView mav = new ModelAndView("sensor_detail");
		mav.addObject("sensor", sensorService.getSensorDevice(id));
		return mav;
	}

	@RequestMapping("/sensor_status/{id}")
	public @ResponseBody SensorDevice getSensorStatus(@PathVariable("id") long id) {
		return sensorService.getSensorDevice(id);
	}
}
