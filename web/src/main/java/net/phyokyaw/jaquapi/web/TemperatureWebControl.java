package net.phyokyaw.jaquapi.web;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.phyokyaw.jaquapi.core.services.AquaService.HistoryInterval;
import net.phyokyaw.jaquapi.temperature.model.TemperatureRecord;
import net.phyokyaw.jaquapi.temperature.services.TemperatureService;

@Controller
public class TemperatureWebControl {

	@Autowired
	@Qualifier("temperature")
	private TemperatureService temperatureService;

	@RequestMapping("/temperature")
	public @ResponseBody TemperatureRecord getTemperature() {
		return temperatureService.getTemperature();
	}

	@RequestMapping("/temp_timeline")
	public String getTempratureHistoryInfo() {
		return "temp_timeline";
	}

	@RequestMapping("/temperature_history")
	public @ResponseBody TemperatureHistoryData getTempRecordHistory(@RequestParam("interval") String interval) {
		HistoryInterval setInterval = HistoryInterval.valueOf(interval);
		return new TemperatureHistoryData(temperatureService.getLastRecords(setInterval), setInterval);
	}

	public static class TemperatureHistoryData {
		private final String[] labels;
		private final double[] values;

		public TemperatureHistoryData(List<TemperatureRecord> records, HistoryInterval interval) {
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
