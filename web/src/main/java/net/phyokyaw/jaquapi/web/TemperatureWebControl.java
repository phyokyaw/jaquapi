package net.phyokyaw.jaquapi.web;

import java.util.List;

import net.phyokyaw.jaquapi.dao.model.TemperatureRecord;
import net.phyokyaw.jaquapi.services.TemperatureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TemperatureWebControl {

	@Autowired
	@Qualifier("temperature")
	private TemperatureService temperatureService;

	@RequestMapping("/temperature")
	public @ResponseBody TemperatureRecord getTemperature() {
		return temperatureService.getTemperature();
	}

	@RequestMapping("/temperature_history")
	public @ResponseBody List<TemperatureRecord> getTempRecordHistory(@RequestParam("days") int days) {
		return temperatureService.getLastRecords(days);
	}
}
