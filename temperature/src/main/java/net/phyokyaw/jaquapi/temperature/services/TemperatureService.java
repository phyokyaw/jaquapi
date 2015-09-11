package net.phyokyaw.jaquapi.temperature.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.phyokyaw.jaquapi.core.services.AquaService;
import net.phyokyaw.jaquapi.core.services.ScheduledService;
import net.phyokyaw.jaquapi.remote.ControllerDataService;
import net.phyokyaw.jaquapi.remote.ValueUpdateListener;
import net.phyokyaw.jaquapi.temperature.dao.TemperatureDao;
import net.phyokyaw.jaquapi.temperature.model.TemperatureRecord;

@Service("temperature")
public class TemperatureService implements AquaService, ValueUpdateListener {
	private static final Logger logger = LoggerFactory.getLogger(TemperatureService.class);

	@Autowired
	private ScheduledService scheduledService;

	@Autowired
	private ControllerDataService controllerDataService;

	@Autowired
	private TemperatureDao dao;
	private double value = 0.0;

	private ScheduledFuture<?> recordSchedule;

	@PreDestroy
	private void shutdown() {
		if (recordSchedule != null) {
			recordSchedule.cancel(false);
		}
	}

	@PostConstruct
	private void setup() {
		controllerDataService.addValueUpdateListener("temp", this);
		recordSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				record();
			}
		}, 1000 * 60);
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
				if (i < records.size()) {
					filteredRecords.add(records.get(i));
				}
			}
		} else if (days == HistoryInterval.DAY) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.add(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			List<TemperatureRecord> records = dao.findByDate(calendar.getTime());
			for (int i = 0; i < records.size(); i += 60) {
				if (i < records.size()) {
					filteredRecords.add(records.get(i));
				}
			}
		} else if (days == HistoryInterval.WEEK) {
			Calendar start = new GregorianCalendar();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Pageable one = new PageRequest(0, 1);
			for (int i = 0; i < 7; i++) {
				List<TemperatureRecord> result = dao.findByDate(calendar.getTime(), start.getTime(), one);
				if (result == null || result.isEmpty()) {
					break;
				}
				filteredRecords.addAll(result);
				start.add(Calendar.DAY_OF_MONTH, -1);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
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

	@Override
	public void setValue(String value) {
		this.value = Double.parseDouble(value);
	}
}
