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
import net.phyokyaw.jaquapi.ph.model.PhRecord;
import net.phyokyaw.jaquapi.ph.services.PhService;

@Controller
public class PhWebControl {

	@Autowired
	@Qualifier("ph")
	private PhService phService;

	@RequestMapping("/ph")
	public @ResponseBody PhRecord getPh() {
		return phService.getPhRecord();
	}

	@RequestMapping("/ph_timeline")
	public String getTempratureHistoryInfo() {
		return "ph_timeline";
	}

	@RequestMapping("/ph_history")
	public @ResponseBody PhFormat getTempRecordHistory(@RequestParam("interval") String interval) {
		HistoryInterval setInterval = HistoryInterval.valueOf(interval);
		return new PhFormat(phService.getLastRecords(setInterval), setInterval);
	}

	public static class PhFormat {
		private final String[] labels;
		private final double[] values;

		public PhFormat(List<PhRecord> records, HistoryInterval interval) {
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
