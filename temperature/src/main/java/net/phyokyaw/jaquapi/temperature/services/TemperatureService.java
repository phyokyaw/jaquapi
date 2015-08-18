package net.phyokyaw.jaquapi.temperature.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.core.services.AquaService;
import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.temperature.dao.TemperatureDao;
import net.phyokyaw.jaquapi.temperature.model.TemperatureRecord;

@Service("temperature")
public class TemperatureService implements AquaService {
	private static final Logger logger = LoggerFactory.getLogger(TemperatureService.class);

	@Autowired
	private ScheduledService scheduledService;
	@Autowired
	private TemperatureDao dao;
	private double value = 0.0;

	private ScheduledFuture<?> updateSchedule;
	private ScheduledFuture<?> recordSchedule;

	@PreDestroy
	private void shutdown() {
		if (updateSchedule != null) {
			updateSchedule.cancel(false);
		}
		if (recordSchedule != null) {
			recordSchedule.cancel(false);
		}
	}

	@PostConstruct
	private void setup() {
		updateSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				update();
			}
		}, 1000 * 20);
		recordSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				record();
			}
		}, 1000 * 60);
	}

	private void update() {
		logger.debug("Updating temp value");
		try {
			Runtime r = Runtime.getRuntime();
			Process p;
			String line;
			p = r.exec(new String[]{"ssh", AquaService.DEVICE_SSH_ADDR, "less", "/sys/bus/w1/devices/28-031466113fff/w1_slave"});
			BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			while ((line = is.readLine()) != null) {
				builder.append(line);
			}
			Pattern pattern = Pattern.compile(".*t=(\\d+)");
			Matcher matcher = pattern.matcher(builder.toString());
			if (matcher.find()) {
				value = Double.parseDouble(matcher.group(1)) / 1000;
			} else {
				throw new Exception("Unable to get Temperature data");
			}
			System.out.flush();
			p.waitFor(); // wait for process to complete
			logger.info("Updating temp value with: " + value);
		} catch (Exception e) {
			logger.error("Error executing temp reader", e);
		}
		// value = Double.valueOf(new DecimalFormat("#.##").format(23 + (new Random().nextDouble() * 2)));

	}

	private void record() {
		try {
			TemperatureRecord record = new TemperatureRecord();
			record.setValue(value);
			dao.save(record);
			logger.info("Saving temp value: " + record.getValue());
		} catch(Exception ex) {
			logger.error("Unable to save", ex);
		}
	}


	public List<TemperatureRecord> getLastRecords(HistoryInterval days) {
		Calendar calendar = new GregorianCalendar();

		List<TemperatureRecord> filteredRecords = new ArrayList<TemperatureRecord>();
		if (days == HistoryInterval.HOUR) {
			calendar.add(Calendar.HOUR_OF_DAY, -1);
			List<TemperatureRecord> records = dao.findByDate(calendar.getTime());
			for (int i = 0; i < records.size(); i += 5) {
				filteredRecords.add(records.get(i));
			}
		} else if (days == HistoryInterval.DAY) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.add(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			List<TemperatureRecord> records = dao.findByDate(calendar.getTime());
			for (int i = 0; i < records.size(); i += 24) {
				filteredRecords.add(records.get(i));
			}
		}
		return filteredRecords;
	}

	public TemperatureRecord getTemperature() {
		TemperatureRecord temp = new TemperatureRecord();
		temp.setValue(this.getValue());
		temp.setStoredTime(new Date());
		return temp;
	}

	@Override
	public double getValue() {
		return value;
	}
}
