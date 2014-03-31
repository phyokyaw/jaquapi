package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.TemperatureService;
import net.phyokyaw.jaquapi.dao.model.TemperatureRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TemperatureWebControl {

	@Autowired
	@Qualifier("temperature")
	private TemperatureService temperatureService;

	@RequestMapping("/temperature")
	public @ResponseBody TemperatureRecord getTemp() {
		return temperatureService.getTemperatureRecord();
	}
}
