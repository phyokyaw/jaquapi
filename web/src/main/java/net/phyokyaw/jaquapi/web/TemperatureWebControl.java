package net.phyokyaw.jaquapi.web;

import java.text.SimpleDateFormat;
import java.util.List;

import net.phyokyaw.jaquapi.temperature.model.TemperatureRecord;
import net.phyokyaw.jaquapi.temperature.services.TemperatureService;
import net.phyokyaw.jaquapi.temperature.services.TemperatureService.HistoryInterval;

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
	public @ResponseBody TemperatureFormat getTempRecordHistory(@RequestParam("interval") String interval) {
		HistoryInterval setInterval = HistoryInterval.valueOf(interval);
		return new TemperatureFormat(temperatureService.getLastRecords(setInterval), setInterval);
	}
	
	public static class TemperatureFormat {
		private final String[] labels;
		private final double[] values;
		
		public TemperatureFormat(List<TemperatureRecord> records, HistoryInterval interval) {
			labels = new String[records.size()];
			values = new double[records.size()];
			for (int i = 0; i < labels.length; i++) {
				labels[i] = new SimpleDateFormat("HH:mm").format(records.get(i).getStoredTime());
				values[i] = records.get(i).getValue();
			}
		}
		
		public String[] getLabels() {
			return labels;
		}
		public double[] getValues() {
			return values;
		}
	}
}
