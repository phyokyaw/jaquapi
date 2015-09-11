package net.phyokyaw.jaquapi.ph.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import net.phyokyaw.jaquapi.ph.dao.PhDao;
import net.phyokyaw.jaquapi.ph.model.PhRecord;
import net.phyokyaw.jaquapi.remote.ControllerDataService;

@Service("ph")
public class PhService implements AquaService {
	private static Logger logger = LoggerFactory.getLogger(PhService.class);
	private double value = 0.0d;
	private static final double OFFSET = -4.8d;

	@Autowired
	private ScheduledService scheduledService;

	@Autowired
	private ControllerDataService controllerDataService;

	@Autowired
	private PhDao dao;

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
		}, 1000 * 20); //20s
		recordSchedule = scheduledService.addScheduleAtFixrate(new Runnable() {
			@Override
			public void run() {
				record();
			}
		}, 1000 * 60); //60s
	}

	private void update() {
		try {
			value = Double.parseDouble(controllerDataService.getPhData()) + OFFSET;
			logger.info("Updated ph value is " + value);
		} catch (NumberFormatException | IOException e) {
			logger.error("Error executing ph reader", e);
		}
	}

	private void record() {
		PhRecord record = new PhRecord();
		record.setValue(value);
		dao.save(record);
		logger.info("Saving ph value: " + record.getValue());
	}

	public List<PhRecord> getLastRecords(HistoryInterval days) {
		Calendar calendar = new GregorianCalendar();

		List<PhRecord> filteredRecords = new ArrayList<PhRecord>();
		if (days == HistoryInterval.HOUR) {
			calendar.add(Calendar.HOUR_OF_DAY, -1);
			List<PhRecord> records = dao.findByDate(calendar.getTime());
			for (int i = 0; i < records.size(); i += 10) {
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
			List<PhRecord> records = dao.findByDate(calendar.getTime());
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
				List<PhRecord> result = dao.findByDate(calendar.getTime(), start.getTime(), one);
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

	public PhRecord getPhRecord() {
		PhRecord ph = new PhRecord();
		ph.setValue(getValue());
		return ph;
	}

	@Override
	public double getValue() {
		return value;
	}

}
