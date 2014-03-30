package net.phyokyaw.jaquapi.web;

import net.phyokyaw.jaquapi.dao.TemperatureService;
import net.phyokyaw.jaquapi.dao.model.TemperatureRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TemperatureWebControl {

	@Autowired
	private TemperatureService temperatureService;

	@RequestMapping("/temperature")
	public @ResponseBody TemperatureRecord getTemp() {
		TemperatureRecord temp = new TemperatureRecord();
		temp.setValue(20.0d);
		return temp;
	}
}
